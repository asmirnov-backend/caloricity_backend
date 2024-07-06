package ru.caloricity.main.caloricity.ingredientCatalog;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;
import ru.caloricity.main.caloricity.ingredient.Ingredient;

import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
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
    @ToString.Exclude
    private Set<Ingredient> ingredients;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        IngredientCatalog that = (IngredientCatalog) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
