package ru.caloricity.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.caloricity.common.BaseEntity;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class EntityNotFoundException extends RuntimeException {
    private final UUID entityId;
    private final Class<? extends BaseEntity> entityClass;
}

