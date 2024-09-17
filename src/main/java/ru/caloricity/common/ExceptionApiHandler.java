package ru.caloricity.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.caloricity.common.exception.EntityNotFoundException;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse notFoundException(EntityNotFoundException ex) {
        return ErrorResponse.builder(ex, ProblemDetail.forStatus(HttpStatus.NOT_FOUND)).title("Сущность не найдена").detail(ex.getEntityClass().getSimpleName() + " с id=" + ex.getEntityId() + " не найдена").build();
    }

}
