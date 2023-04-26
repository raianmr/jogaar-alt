package com.jogaar.entities;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "campaigns")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {
    public enum State {
        DRAFT, STARTED, ENDED, LOCKED, GREENLIT,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "campaigner_id", referencedColumnName = "id", nullable = false)
    private User campaigner;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = true)
    private String challenges;

    @Column(nullable = true)
    private String faqs;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_id", referencedColumnName = "id", nullable = true)
    private Image cover;

    @Column(name = "goal_amount", nullable = false)
    private Long goalAmount;

    @Builder.Default
    @Column(name = "pledged_amount", nullable = false)
    private Long pledgedAmount = 0L;

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime deadline = LocalDateTime.now().plusMonths(1);;

    @Builder.Default
    @Column(name = "current_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private State currentState = State.DRAFT;
}
