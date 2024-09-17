package ru.caloricity.probeingredient;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
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
public class ProbeIngredient extends BaseEntity {
    @Comment("Масса брутто, г")
    @Column(nullable = false)
    private Float gross;

    @Comment("Масса нетто, г")
    @Column(nullable = false)
    private Float net;

    @ManyToOne(optional = false)
    Probe probe;

    @ManyToOne(optional = false)
    Ingredient ingredient;

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
