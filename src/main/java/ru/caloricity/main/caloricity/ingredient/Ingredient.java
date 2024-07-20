package ru.caloricity.main.caloricity.ingredient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;
import ru.caloricity.main.caloricity.ingredientCatalog.IngredientCatalog;
import ru.caloricity.main.caloricity.probe.Probe;
import ru.caloricity.main.common.BaseEntity;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Comment("Ингредиенты")
@Table(name = "ingredients")
public class Ingredient extends BaseEntity {
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

    @ManyToOne
    private Probe probe;

    @Comment("Ингредиент в справочнике")
    @ManyToOne
    private IngredientCatalog ingredientInCatalog;

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
