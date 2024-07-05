package ru.caloricity.main.caloricity.сarbohydratesResearch;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import ru.caloricity.main.caloricity.probe.Probe;

import java.util.UUID;

@Data
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
}
