package ru.caloricity.probe.research.carbohydratesresearch;

import jakarta.persistence.Entity;
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
@NoArgsConstructor
@Comment("Исследования на углеводы")
@Table(name = "carbohydrates_researches")
@AllArgsConstructor
@Builder
public class CarbohydratesResearch extends BaseEntity {
    @Comment("Масса бюксы первая параллель, г")
    @NotNull
    private Double byuksaParallelFirst;

    @Comment("Масса бюксы вторая параллель, г")
    @NotNull
    private Double byuksaParallelSecond;

    @Comment("Масса бюксы с пробой после высушивания первая параллель, г")
    @NotNull
    private Double byuksaAfterDryingParallelFirst;

    @Comment("Масса бюксы с пробой после высушивания вторая параллель, г")
    @NotNull
    private Double byuksaAfterDryingParallelSecond;

    // углеводы есть только в 3-ем блюде и они рассчитываются через сухие вещества
    // 2 парралели, среднее
    // return сухие вещества - минеральные вещества -> для третьего блюда
    // return сухие вещества - масса белков - масса мин веществ - масса жиров -> для первого и второго блюда

    // сухие вещества теоретическая (Есть для всех исследований)
    // считается как сумма по всем ингредиентам в пробе

    @OneToOne(mappedBy = "carbohydratesResearch")
    private Probe probe;

    public Double getDryResidueWeightParallelFirst() {
        return byuksaParallelFirst - byuksaAfterDryingParallelFirst;
    }

    public Double getDryResidueWeightParallelSecond() {
        return byuksaParallelSecond - byuksaAfterDryingParallelSecond;
    }

    public Double getDryResidueWeightAverage() {
        return (getDryResidueWeightParallelFirst() + getDryResidueWeightParallelSecond()) / 2.0;
    }

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
