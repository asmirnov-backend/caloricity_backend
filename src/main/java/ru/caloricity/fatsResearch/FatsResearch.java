package ru.caloricity.fatsResearch;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;
import ru.caloricity.common.BaseEntity;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Comment("Исследования на жиры")
@Table(name = "fats_research")
public class FatsResearch extends BaseEntity {
    @Comment("Масса патрона до экстракции, г")
    @Column(nullable = false)
    private Float patronMassBeforeExtraction;

    @Comment("Масса патрона после экстракции, г")
    @Column(nullable = false)
    private Float patronMassAfterExtraction;

//    @OneToOne(mappedBy = "fatsResearch")
//    private Probe probe;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        FatsResearch that = (FatsResearch) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
