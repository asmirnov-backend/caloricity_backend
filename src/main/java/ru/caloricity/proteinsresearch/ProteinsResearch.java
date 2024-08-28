package ru.caloricity.proteinsresearch;

import jakarta.persistence.*;
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
@Comment("Исследования на белок")
@Table(name = "proteins_researches")
public class ProteinsResearch extends BaseEntity {
    @Comment("Объём титранта первая параллель, г/см^3")
    @Column(nullable = false)
    private Float titrantVolumeParallelFirst;

    @Comment("Объём титранта вторая параллель, г/см^3")
    @Column(nullable = false)
    private Float titrantVolumeParallelSecond;

    @Comment("Объём контроля, г/см^3")
    @Column(nullable = false)
    private Float controlVolume;

    @Comment("Коэффициент")
    @Column(nullable = false)
    private Float coefficient;

    @OneToOne(optional = false)
    @JoinColumn(name = "probe_id", unique = true)
    private Probe probe;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ProteinsResearch that = (ProteinsResearch) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
