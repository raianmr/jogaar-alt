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
        name = "milestones",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
//                        "campaign_id",
//                        "deadline",
                }
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Milestone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

//    @ManyToOne
//    @JoinColumn(name = "campaign_id", nullable = false)
//    private Campaign campaign;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

//    @ManyToOne
//    @JoinColumn(name = "picture_id", onDelete = "CASCADE")
//    private Image picture;

    @Column(nullable = false)
    private LocalDateTime deadline = LocalDateTime.now().plusWeeks(1);;
}
