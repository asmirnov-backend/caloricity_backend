package ru.caloricity.common.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {super("Сущность не найдена");}
}
