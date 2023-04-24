package com.jogaar.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {
    public enum State {
        DRAFT, STARTED, ENDED, LOCKED, GREENLIT,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = true)
    private String challenges;

    @Column(nullable = true)
    private Long coverId;

    @Column(nullable = false)
    private Long goalAmount;

    @Column(nullable = false)
    private Long pledgedAmount = 0L;

    @Column(nullable = false)
    private LocalDateTime deadline = LocalDateTime.now().plusMonths(1);;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State currentState = State.DRAFT;
}
