package com.jogaar.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "pledges",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
//                        "campaign_id",
//                        "pledger_id",
                }
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pledge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pledger_id", nullable = false)
//    private User pledger;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "campaign_id", nullable = false)
//    private Campaign campaign;

    @Column(nullable = false)
    private Long amount;
}
