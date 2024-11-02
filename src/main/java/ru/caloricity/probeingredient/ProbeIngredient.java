package ru.caloricity.probeingredient;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.proxy.HibernateProxy;
import ru.caloricity.common.BaseEntity;
import ru.caloricity.ingredient.Ingredient;
import ru.caloricity.probe.Probe;

import java.util.Objects;

@Entity
@Table(name = "probe_ingredient", indexes = @Index(columnList = "probeId, ingredientId", unique = true))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProbeIngredient extends BaseEntity {
    @Comment("Масса брутто, г")
    @NotNull
    private Double gross;

    @Comment("Масса нетто, г")
    @NotNull
    private Double net;

    @ManyToOne(optional = false)
    Probe probe;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    Ingredient ingredient;

    Double drySubstances() {
        return net - (net * ingredient.getWater()) / 100;
    }

    Double proteins() {
        return net * ingredient.getProteins() / 100;
    }

    Double fats() {
        return net * ingredient.getFats() / 100;
    }

    Double carbohydrates() {
        return net * ingredient.getCarbohydrates() / 100;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ProbeIngredient that = (ProbeIngredient) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
