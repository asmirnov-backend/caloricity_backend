package ru.caloricity.proteinsResearch;

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
@Comment("Исследования на белок")
@Table(name = "proteins_researches")
public class ProteinsResearch extends BaseEntity {
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

//    @OneToOne(mappedBy = "proteinsResearch")
//    private Probe probe;

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
