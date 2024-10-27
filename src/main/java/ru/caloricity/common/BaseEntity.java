package ru.caloricity.common;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    @Id
    @NotNull
    protected UUID id;

    @Comment("Дата и время создания")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, nullable = false)
    protected LocalDateTime createdAt;

    @Comment("Дата и время редактирования")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    protected LocalDateTime updatedAt;

    /**
     * Easier approach then {@code @GeneratorValue}
     */
    @PrePersist
    protected void generateIdIfItIsNull() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }
}
