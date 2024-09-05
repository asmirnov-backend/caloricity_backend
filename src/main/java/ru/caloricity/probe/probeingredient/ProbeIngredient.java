package ru.caloricity.probe.probeingredient;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;
import ru.caloricity.ingredient.Ingredient;
import ru.caloricity.probe.Probe;

import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "probe_ingredient")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProbeIngredient {
    @EmbeddedId
    ProbeIngredientKey id;

    @Comment("Масса брутто, г")
    @Column(nullable = false)
    private Float gross;

    @Comment("Масса нетто, г")
    @Column(nullable = false)
    private Float net;

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
    @MapsId("probeId")
    Probe probe;

    @ManyToOne
    @MapsId("ingredientId")
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
        return Objects.hash(id);
    }
}
