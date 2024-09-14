package ru.caloricity.common.exception;

import lombok.Getter;
import ru.caloricity.common.BaseEntity;

import java.util.UUID;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private final UUID entityId;
    private final Class<? extends BaseEntity> entityClass;

    public EntityNotFoundException (UUID entityId, Class<? extends BaseEntity> entityClass) {
        super((String.format("Entity '%s' with id=%s not found", entityClass.getName(), entityId)));
        this.entityId = entityId;
        this.entityClass = entityClass;
    }
}

