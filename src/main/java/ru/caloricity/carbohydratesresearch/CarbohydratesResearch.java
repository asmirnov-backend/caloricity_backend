package ru.caloricity.carbohydratesresearch;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;
import ru.caloricity.common.BaseEntity;
import ru.caloricity.probe.Probe;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Comment("Исследования на углеводы")
@Table(name = "carbohydrates_researches")
public class CarbohydratesResearch extends BaseEntity {
    @Comment("Масса бюксы первая параллель, г")
    @Column(nullable = false)
    private Float byuksaParallelFirst;

    @Comment("Масса бюксы вторая параллель, г")
    @Column(nullable = false)
    private Float byuksaParallelSecond;

    @Comment("Масса бюксы с пробой после высушивания первая параллель, г")
    @Column(nullable = false)
    private Float byuksaAfterDryingParallelFirst;

    @Comment("Масса бюксы с пробой после высушивания вторая параллель, г")
    @Column(nullable = false)
    private Float byuksaAfterDryingParallelSecond;

    @OneToOne(optional = false)
    @JoinColumn(unique = true)
    private Probe probe;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CarbohydratesResearch that = (CarbohydratesResearch) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
