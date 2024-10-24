package ru.caloricity.probe;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;
import ru.caloricity.carbohydratesresearch.CarbohydratesResearch;
import ru.caloricity.common.BaseEntity;
import ru.caloricity.drysubstancesresearch.DrySubstancesResearch;
import ru.caloricity.fatsresearch.FatsResearch;
import ru.caloricity.probeingredient.ProbeIngredient;
import ru.caloricity.proteinsresearch.ProteinsResearch;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Comment("Пробы блюд")
@Table(name = "probes")
public class Probe extends BaseEntity {
    @Comment("Наименование пробы")
    @NotNull
    private String name;

    @Comment("Тип пробы")
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProbeType type;

    @Comment("Код пробы")
    @NotNull
    private String code;

    @Comment("Масса теоретическая, г")
    @NotNull
    private Float massTheory;

    @Comment("Масса пустой банки, г")
    @NotNull
    private Float bankaEmptyMass;

    @Comment("Масса банки с пробой, г")
    @NotNull
    private Float bankaWithProbeMass;

    @OneToMany(mappedBy = "probe", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<ProbeIngredient> probeIngredient;

    @OneToOne(mappedBy = "probe", cascade = CascadeType.ALL, orphanRemoval = true)
    private CarbohydratesResearch carbohydratesResearch;

    @OneToOne(mappedBy = "probe", cascade = CascadeType.ALL, orphanRemoval = true)
    private DrySubstancesResearch drySubstancesResearch;

    @OneToOne(mappedBy = "probe", cascade = CascadeType.ALL, orphanRemoval = true)
    private FatsResearch fatsResearch;

    @OneToOne(mappedBy = "probe", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProteinsResearch proteinsResearch;

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
