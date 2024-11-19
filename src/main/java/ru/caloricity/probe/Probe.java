package ru.caloricity.probe;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;
import ru.caloricity.common.BaseEntity;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearch;
import ru.caloricity.probe.research.fatsresearch.FatsResearch;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearch;
import ru.caloricity.probeingredient.ProbeIngredient;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Comment("Пробы блюд")
@Table(name = "probes")
@Builder
@AllArgsConstructor
@NamedEntityGraph(
        name = "Probe.withResearchesAndIngredients",
        attributeNodes = {
                @NamedAttributeNode("drySubstancesResearch"),
                @NamedAttributeNode("fatsResearch"),
                @NamedAttributeNode("proteinsResearch"),
                @NamedAttributeNode(value = "probeIngredients", subgraph = "ingredientSubgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "ingredientSubgraph",
                        attributeNodes = {
                                @NamedAttributeNode("ingredient")
                        }
                )
        }
)
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
    private Double massTheory;

    @Comment("Масса пустой банки, г")
    @NotNull
    private Double bankaEmptyMass;

    @Comment("Масса банки с пробой, г")
    @NotNull
    private Double bankaWithProbeMass;

    @OneToMany(mappedBy = "probe")
    @ToString.Exclude
    private Set<ProbeIngredient> probeIngredients;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true)
    private DrySubstancesResearch drySubstancesResearch;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true)
    private FatsResearch fatsResearch;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true)
    private ProteinsResearch proteinsResearch;

    /**
     * @return Масса фактическая, г
     */
    public Double getMassFact() {
        return bankaWithProbeMass - bankaEmptyMass;
    }

    /**
     * @return Минеральные вещества, г
     */
    public Double getMinerals() {
        return getMassFact() * type.coefficientOfMinerals;
    }

    public Double getTheoreticalCaloricity() {
        return Optional.ofNullable(probeIngredients)
                .stream()
                .flatMap(Collection::stream)
                .map(e -> e.getIngredient().getTheoreticalCaloricity() / 100 * e.getNet())
                .mapToDouble(Double::doubleValue)
                .sum();
    }
    // калорийность
    // считается как (белки + углеводы )* 4 + жиры * 9
    // теоретическая и фактическая считается, и отклонение

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
