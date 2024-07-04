package ru.caloricity.main.caloricity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.caloricity.main.caloricity.ingredientCatalog.IngredientCatalog;

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

    @Comment("Масса брутто, г")
    @Column(nullable = false)
    private float gross;

    @Comment("Масса нетто, г")
    @Column(nullable = false)
    private float net;

    @Comment("Масса сухих веществ, г")
    @Column(nullable = false)
    private float drySubstances;

    @Comment("Масса белков, г")
    @Column(nullable = false)
    private float proteins;

    @Comment("Масса жиров, г")
    @Column(nullable = false)
    private float fats;

    @Comment("Масса углеводов, г")
    @Column(nullable = false)
    private float carbohydrates;

    @Comment("Дата и время создания")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, nullable = false)
    private Date createdAt;

    @Comment("Дата и время редактирования")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    @ManyToOne
    private Probe probe;

    @Comment("Ингредиент в справочнике")
    @ManyToOne
    private IngredientCatalog ingredientInCatalog;
}
