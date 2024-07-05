package ru.caloricity.main.caloricity.fatsResearch;

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
@Comment("Исследования на жиры")
@Table(name = "fats_research")
public class FatsResearch {
    @Id
    @Column(nullable = false)
    private UUID id;

    @Comment("Масса патрона до экстракции, г")
    @Column(nullable = false)
    private float patronMassBeforeExtraction;

    @Comment("Масса патрона после экстракции, г")
    @Column(nullable = false)
    private float patronMassAfterExtraction;

    @OneToOne(mappedBy = "fatsResearch")
    private Probe probe;
}
