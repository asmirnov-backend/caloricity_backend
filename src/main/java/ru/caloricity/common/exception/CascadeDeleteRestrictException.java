package ru.caloricity.common.exception;

import lombok.Getter;
import ru.caloricity.common.BaseEntity;

import java.util.UUID;

@Getter
public class CascadeDeleteRestrictException extends RuntimeException {
    private final UUID entityId;
    private final Class<? extends BaseEntity> entityClass;
    private final Class<? extends BaseEntity> linkedEntityClass;

    public CascadeDeleteRestrictException(UUID entityId, Class<? extends BaseEntity> entityClass, Class<? extends BaseEntity> linkedEntityClass) {
        super((String.format("Entity '%s' with id=%s has cascade restrict relation with '%s'", entityClass.getName(), entityId, linkedEntityClass)));
        this.entityId = entityId;
        this.entityClass = entityClass;
        this.linkedEntityClass = linkedEntityClass;
    }
}

