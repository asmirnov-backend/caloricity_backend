package ru.caloricity.ingredientcatalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;
import ru.caloricity.common.BaseEntity;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Comment("Каталог ингредиентов")
@Table(name = "ingredients_catalog")
public class IngredientCatalog extends BaseEntity {
    @Comment("Наименование ингредиента")
    @Column(length = 127, nullable = false)
    private String name;

    @Comment("Масса съедобной части, г")
    @Column(nullable = false)
    private Float ediblePart;

    @Comment("Масса воды, г")
    @Column(nullable = false)
    private Float water;

    @Comment("Масса белков, г")
    @Column(nullable = false)
    private Float proteins;

    @Comment("Масса жиров, г")
    @Column(nullable = false)
    private Float fats;

    @Comment("Масса углеводов, г")
    @Column(nullable = false)
    private Float carbohydrates;

//    @OneToMany(fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private Set<Ingredient> ingredients;

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
