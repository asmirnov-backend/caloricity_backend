package ru.caloricity.ingredient;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;
import ru.caloricity.common.BaseEntity;
import ru.caloricity.probeingredient.ProbeIngredient;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Comment("Каталог ингредиентов")
@Table(name = "ingredients")
public class Ingredient extends BaseEntity {
    @Comment("Наименование ингредиента")
    @NotNull
    private String name;

    @Comment("Съедобная часть, доля (От 0 до 1)")
    @NotNull
    private Float ediblePart;

    @Comment("Масса воды, г")
    @NotNull
    private Float water;

    @Comment("Масса белков, г")
    @NotNull
    private Float proteins;

    @Comment("Масса жиров, г")
    @NotNull
    private Float fats;

    @Comment("Масса углеводов, г")
    @NotNull
    private Float carbohydrates;

    @OneToMany(mappedBy = "ingredient")
    @ToString.Exclude
    private Set<ProbeIngredient> probeIngredients;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Ingredient that = (Ingredient) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
