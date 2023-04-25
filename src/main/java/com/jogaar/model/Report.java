package com.jogaar.model;

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
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    public enum Reportable {
        USER, CAMPAIGN, REPLY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", referencedColumnName = "id", nullable = false)
    private User reporter;

    @Column(nullable = false)
    private String description;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    @Column(name = "content_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Reportable contentType = Reportable.CAMPAIGN;
}
