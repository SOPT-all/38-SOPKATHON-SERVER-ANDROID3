package org.sopt.sopkathon.domain.entity;

import jakarta.persistence.*;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

