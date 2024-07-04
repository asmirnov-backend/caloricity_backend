package ru.caloricity.main.caloricity.ingredientCatalog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.caloricity.main.caloricity.Ingredient;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Comment("Каталог ингредиентов")
@Table(name = "ingredients_catalog")
public class IngredientCatalog {
    @Id
    @Column(nullable = false)
    private UUID id;

    @Comment("Наименование ингредиента")
    @Column(length = 127, nullable = false)
    private String name;

    @Comment("Масса съедобной части, г")
    @Column(nullable = false)
    private float ediblePart;

    @Comment("Масса воды, г")
    @Column(nullable = false)
    private float water;

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

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Ingredient> ingredients;
}
