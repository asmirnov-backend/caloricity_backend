package ru.caloricity.main.caloricity.drySubstancesResearch;

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
@Comment("Исследования на сухие остатки")
@Table(name = "dry_substances_researches")
public class DrySubstancesResearch {
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

    @Comment("Масса навески, г")
    @Column(nullable = false)
    private float mass;

    @OneToOne(mappedBy = "drySubstancesResearch")
    private Probe probe;

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
