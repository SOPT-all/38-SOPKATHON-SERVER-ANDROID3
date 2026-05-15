#!/bin/bash

# ================================================================
# Blue/Green 무중단 배포 스크립트
# Blue  : 8081
# Green : 8082
# ================================================================

DEPLOY_PATH=/home/ubuntu/app/sopkathon
JAR_NAME=sopkathon.jar
NGINX_INC=/etc/nginx/conf.d/sopkathon-service-url.inc

HEALTH_CHECK_RETRY=20
HEALTH_CHECK_INTERVAL=5

# ── 현재 활성 포트 확인 ─────────────────────────────────────────
CURRENT_PORT=$(cat "$DEPLOY_PATH/current_port" 2>/dev/null || echo "8082")

if [ "$CURRENT_PORT" = "8081" ]; then
    NEXT_PORT=8082
else
    NEXT_PORT=8081
fi

echo "▶ 현재 포트: $CURRENT_PORT  →  새 포트: $NEXT_PORT"

# ── 로그 디렉터리 준비 ──────────────────────────────────────────
mkdir -p "$DEPLOY_PATH/logs"

# ── 새 포트에 남아있는 구 프로세스 종료 ─────────────────────────
PREV_PID=$(lsof -ti tcp:"$NEXT_PORT" 2>/dev/null)
if [ -n "$PREV_PID" ]; then
    echo "▶ $NEXT_PORT 포트 구 프로세스 종료 (PID: $PREV_PID)"
    kill -15 "$PREV_PID"
    sleep 3
fi

# ── 새 인스턴스 실행 ────────────────────────────────────────────
echo "▶ $NEXT_PORT 포트에서 새 인스턴스 시작"
nohup java \
    -Dserver.port="$NEXT_PORT" \
    -jar "$DEPLOY_PATH/$JAR_NAME" \
    > "$DEPLOY_PATH/logs/app-$NEXT_PORT.log" 2>&1 &

echo $! > "$DEPLOY_PATH/pid-$NEXT_PORT.pid"

# ── Health Check ────────────────────────────────────────────────
echo "▶ Health Check 시작 (최대 $((HEALTH_CHECK_RETRY * HEALTH_CHECK_INTERVAL))초)"

for i in $(seq 1 $HEALTH_CHECK_RETRY); do
    sleep $HEALTH_CHECK_INTERVAL

    HTTP_STATUS=$(curl -s -o /dev/null -w "%{http_code}" \
        http://localhost:"$NEXT_PORT"/ 2>/dev/null || echo "000")

    # 000 이 아니면 서버가 응답 중 (404·200 모두 기동 완료로 간주)
    if [ "$HTTP_STATUS" != "000" ]; then
        echo "▶ Health Check 통과 ($i/$HEALTH_CHECK_RETRY, HTTP $HTTP_STATUS)"
        break
    fi

    echo "  대기 중... ($i/$HEALTH_CHECK_RETRY)"

    if [ "$i" -eq "$HEALTH_CHECK_RETRY" ]; then
        echo "✖ Health Check 실패 — 롤백합니다"
        FAIL_PID=$(cat "$DEPLOY_PATH/pid-$NEXT_PORT.pid" 2>/dev/null)
        [ -n "$FAIL_PID" ] && kill -15 "$FAIL_PID"
        exit 1
    fi
done

# ── Nginx 트래픽 전환 ───────────────────────────────────────────
echo "▶ Nginx 트래픽 전환 → $NEXT_PORT"
echo "set \$service_url http://127.0.0.1:$NEXT_PORT;" | sudo tee "$NGINX_INC" > /dev/null
sudo nginx -s reload

# ── 구 인스턴스 Graceful Shutdown ───────────────────────────────
OLD_PID=$(lsof -ti tcp:"$CURRENT_PORT" 2>/dev/null)
if [ -n "$OLD_PID" ]; then
    echo "▶ 구 인스턴스 종료 (PID: $OLD_PID, PORT: $CURRENT_PORT)"
    kill -15 "$OLD_PID"
fi

# ── 상태 파일 업데이트 ──────────────────────────────────────────
echo "$NEXT_PORT" > "$DEPLOY_PATH/current_port"

echo "✔ 배포 완료 — 서비스 포트: $NEXT_PORT"
