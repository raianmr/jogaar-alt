package com.jogaar.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "pledges",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
                        "pledger_id",
                        "campaign_id",
                }
        )
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pledge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pledger_id", referencedColumnName = "id", nullable = false)
    private User pledger;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", referencedColumnName = "id", nullable = false)
    private Campaign campaign;

    @Column(nullable = false)
    private Long amount;
}
