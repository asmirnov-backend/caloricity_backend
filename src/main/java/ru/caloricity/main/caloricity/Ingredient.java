package ru.caloricity.main.caloricity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Comment("Ингредиенты")
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(length = 127, nullable = false)
    private String name;

    @Comment("Масса брутто")
    @Column(nullable = false)
    private float gross_g;

    @Comment("Масса нетто")
    @Column(nullable = false)
    private float net_g;

    @Comment("Масса сухих веществ")
    @Column(nullable = false)
    private float dry_substances_g;

    @Column(nullable = false)
    private float protein_g;

    @Column(nullable = false)
    private float fats_g;

    @Column(nullable = false)
    private float carbohydrates_g;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    @ManyToOne
    private Probe probe;
}
