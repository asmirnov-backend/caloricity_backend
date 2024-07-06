package ru.caloricity.main.caloricity.сarbohydratesResearch;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;
import ru.caloricity.main.caloricity.probe.Probe;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Comment("Исследования на углеводы")
@Table(name = "carbohydrates_researches")
public class CarbohydratesResearch {
    @Id
    @Column(nullable = false)
    private UUID id;

    @Comment("Масса бюксы первая параллель, г")
    @Column(nullable = false)
    private float byuksaParallelFirst;

    @Comment("Масса бюксы вторая параллель, г")
    @Column(nullable = false)
    private float byuksaParallelSecond;

    @Comment("Масса пустой банки, г")
    @Column(nullable = false)
    private float bankaEmptyMass;

    @Comment("Масса банки с пробой, г")
    @Column(nullable = false)
    private float bankaWithProbeMass;

    @Comment("Масса навески, г")
    @Column(nullable = false)
    private float mass;

    @OneToOne(mappedBy = "carbohydratesResearch")
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
