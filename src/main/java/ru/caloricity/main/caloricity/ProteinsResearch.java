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
@Comment("Исследования на белок")
@Table(name = "proteins_researches")
public class ProteinsResearch {
    @Id
    @Column(nullable = false)
    private UUID id;

    @Comment("Объём титранта, г/см^3")
    @Column(nullable = false)
    private float titrantVolume;

    @Comment("Масса навески, г")
    @Column(nullable = false)
    private float mass;

    @Comment("Объём контроля, г/см^3")
    @Column(nullable = false)
    private float controlVolume;

    @Comment("Коэффициент")
    @Column(nullable = false)
    private float coefficient;

    @OneToOne(mappedBy = "proteinsResearch")
    private Probe probe;
}
