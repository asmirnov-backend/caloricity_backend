package ru.caloricity.drysubstancesresearch;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.proxy.HibernateProxy;
import ru.caloricity.common.BaseEntity;
import ru.caloricity.probe.Probe;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Comment("Исследования на сухие остатки")
@Table(name = "dry_substances_researches")
@Builder
public class DrySubstancesResearch extends BaseEntity {
    @Comment("Масса бюксы первая параллель, г")
    @NotNull
    private Double byuksaParallelFirst;

    @Comment("Масса бюксы вторая параллель, г")
    @NotNull
    private Double byuksaParallelSecond;

    @Comment("Масса навески первая параллель, г")
    @NotNull
    private Double massNaveskiParallelFirst;

    @Comment("Масса навески вторая параллель, г")
    @NotNull
    private Double massNaveskiParallelSecond;

    @Comment("Масса бюксы с пробой после высушивания первая параллель, г")
    @NotNull
    private Double byuksaAfterDryingParallelFirst;

    @Comment("Масса бюксы с пробой после высушивания вторая параллель, г")
    @NotNull
    private Double byuksaAfterDryingParallelSecond;

    @OneToOne(optional = false)
    @JoinColumn(unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Probe probe;

    public Double getDryResidueWeightParallelFirst() {
        // это для первого и второго
        return byuksaParallelFirst - byuksaAfterDryingParallelFirst; // + Масса пробы первая параллель, г

        // а для третьего блюда
        // масса пробы == масса навески
        // (byuksaAfterDryingParallelFirst - byuksaParallelFirst) * масса фактичиская пробы / Масса пробы первая параллель;
    }

    public Double getDryResidueWeightParallelSecond() {
        return byuksaParallelSecond - byuksaAfterDryingParallelSecond; // + Масса пробы второй параллель, г
    }

    public Double getDryResidueWeightAverage() {
        return (getDryResidueWeightParallelFirst() + getDryResidueWeightParallelSecond()) / 2.0f;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        DrySubstancesResearch that = (DrySubstancesResearch) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
