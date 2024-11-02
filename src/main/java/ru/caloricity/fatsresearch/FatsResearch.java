package ru.caloricity.fatsresearch;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;
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
@Comment("Исследования на жиры")
@Table(name = "fats_research")
@Builder
public class FatsResearch extends BaseEntity {
    @Comment("Масса патрона до экстракции первая параллель, г")
    @NotNull
    private Float patronMassBeforeExtractionParallelFirst;

    @Comment("Масса патрона до экстракции вторая параллель, г")
    @NotNull
    private Float patronMassBeforeExtractionParallelSecond;

    @Comment("Масса патрона после экстракции первая параллель, г")
    @NotNull
    private Float patronMassAfterExtractionParallelFirst;

    @Comment("Масса патрона после экстракции вторая параллель, г")
    @NotNull
    private Float patronMassAfterExtractionParallelSecond;

    @OneToOne(optional = false)
    @JoinColumn(unique = true)
    private Probe probe;

    public Float getDryResidueWeightParallelFirst() {
        return patronMassBeforeExtractionParallelFirst - patronMassAfterExtractionParallelFirst;
    }

    public Float getDryResidueWeightParallelSecond() {
        return patronMassBeforeExtractionParallelSecond - patronMassAfterExtractionParallelSecond;
    }

    public Float getDryResidueWeightAverage() {
        return (getDryResidueWeightParallelFirst() + getDryResidueWeightParallelSecond()) / 2.0f;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        FatsResearch that = (FatsResearch) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
