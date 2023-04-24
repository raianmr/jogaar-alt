package com.jogaar.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
        name = "reports",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
//                        "reporterId",
                        "contentType",
                        "contentId"
                }
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    public enum Reportable {
        USER, CAMPAIGN, REPLY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "reporter_id", nullable = false)
//    private User reporter;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long contentId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Reportable contentType = Reportable.CAMPAIGN;
}
