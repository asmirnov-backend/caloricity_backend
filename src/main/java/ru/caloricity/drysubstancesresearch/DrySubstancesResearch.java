package ru.caloricity.drysubstancesresearch;

import jakarta.persistence.*;
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
public class DrySubstancesResearch extends BaseEntity {
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
    @OnDelete(action = OnDeleteAction.CASCADE)
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
