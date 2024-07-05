package ru.caloricity.main.caloricity.probe;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.caloricity.main.caloricity.proteinsResearch.ProteinsResearch;
import ru.caloricity.main.caloricity.drySubstancesResearch.DrySubstancesResearch;
import ru.caloricity.main.caloricity.fatsResearch.FatsResearch;
import ru.caloricity.main.caloricity.сarbohydratesResearch.CarbohydratesResearch;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Comment("Пробы блюд")
@Table(name = "probes")
public class Probe {
    @Id
    @Column(nullable = false)
    private UUID id;

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
    @Column(nullable = false)
    private float massTheory;

    @Comment("Масса фактическая, г")
    @Column()
    private float massFact;

    @Comment("Дата отбора пробы")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date selectedAt;

    @Comment("Дата и время создания")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, nullable = false)
    private Date createdAt;

    @Comment("Дата и время редактирования")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    @OneToOne(cascade = CascadeType.REMOVE)
    private ProteinsResearch proteinsResearch;

    @OneToOne(cascade = CascadeType.REMOVE)
    private FatsResearch fatsResearch;

    @OneToOne(cascade = CascadeType.REMOVE)
    private DrySubstancesResearch drySubstancesResearch;

    @OneToOne(cascade = CascadeType.REMOVE)
    private CarbohydratesResearch carbohydratesResearch;
}
