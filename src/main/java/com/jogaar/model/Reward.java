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
        name = "rewards",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
//                        "campaignId",
                        "pledgeAmount",
                }
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "campaign_id", referencedColumnName = "id", nullable = false)
//    private Campaign campaign;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "picture_id", referencedColumnName = "id", nullable = true)
//    private Image picture;

    @Column(nullable = false)
    private Integer pledgeAmount;
}
