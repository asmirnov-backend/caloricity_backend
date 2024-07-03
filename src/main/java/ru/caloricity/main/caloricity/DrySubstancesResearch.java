package ru.caloricity.main.caloricity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.UUID;

@Data
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
}
