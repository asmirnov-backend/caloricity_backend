package ru.caloricity.probe;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;
import ru.caloricity.carbohydratesResearch.CarbohydratesResearch;
import ru.caloricity.common.BaseEntity;
import ru.caloricity.drySubstancesResearch.DrySubstancesResearch;
import ru.caloricity.fatsResearch.FatsResearch;
import ru.caloricity.proteinsResearch.ProteinsResearch;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Comment("Пробы блюд")
@Table(name = "probes")
public class Probe extends BaseEntity {
    @Comment("Наименование пробы")
    @Column(length = 127, nullable = false)
    private String name;

    @Comment("Тип пробы")
    @Column(length = 127, nullable = false)
    @Enumerated(EnumType.STRING)
    private ProbeType type;

    @Comment("Код пробы")
    @Column(length = 127, nullable = false)
    private String code;

    @Comment("Масса теоретическая, г")
    @Column()
    private Float massTheory;

    @Comment("Масса фактическая, г")
    @Column(nullable = false)
    private Float massFact;

    @OneToOne(cascade = CascadeType.REMOVE)
    private ProteinsResearch proteinsResearch;

    @OneToOne(cascade = CascadeType.REMOVE)
    private FatsResearch fatsResearch;

    @OneToOne(cascade = CascadeType.REMOVE)
    private DrySubstancesResearch drySubstancesResearch;

    @OneToOne(cascade = CascadeType.REMOVE)
    private CarbohydratesResearch carbohydratesResearch;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Probe probe = (Probe) o;
        return getId() != null && Objects.equals(getId(), probe.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
