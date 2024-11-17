package ru.caloricity.common;

import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.caloricity.common.exception.CascadeDeleteRestrictException;
import ru.caloricity.common.exception.EntityNotFoundException;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse notFoundException(EntityNotFoundException ex) {
        return ErrorResponse.builder(ex, ProblemDetail.forStatus(HttpStatus.NOT_FOUND)).title("Сущность не найдена").detail(ex.getEntityClass().getSimpleName() + " с id=" + ex.getEntityId() + " не найдена").build();
    }

    @ExceptionHandler(CascadeDeleteRestrictException.class)
    public ErrorResponse notFoundException(CascadeDeleteRestrictException ex) {
        return ErrorResponse.builder(ex, ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)).title("Связанные сущности не позволяют произвести удаление").detail("У " + ex.getEntityClass().getSimpleName() + " с id=" + ex.getEntityId() + " есть зависимости с " + ex.getLinkedEntityClass().getSimpleName()).build();
    }

    @ExceptionHandler(OptimisticLockException.class)
    public ErrorResponse optimisticLockException(OptimisticLockException ex) {
        return ErrorResponse.builder(ex, ProblemDetail.forStatus(HttpStatus.CONFLICT)).title("Данные, с которыми вы работали, были изменены другим пользователем. Пожалуйста, начните\n" +
                "выполнение задачи повторно с измененными данными.").detail(ex.getMessage()).build();
    }

}
