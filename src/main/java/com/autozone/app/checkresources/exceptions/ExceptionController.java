package com.autozone.app.checkresources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * The type Exception controller.
 */
@ControllerAdvice
public class ExceptionController {

    /**
     * Handler bad request exception response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Error> handlerBadRequestException(BadRequestException exception) {
        return exceptions(HttpStatus.BAD_REQUEST, exception.getMessage(), "Bad Request");
    }

    /**
     * Handler forbidden exception response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Error> handlerForbiddenException(ForbiddenException exception) {
        return exceptions(HttpStatus.FORBIDDEN, exception.getMessage(), "Forbidden");
    }

    /**
     * Handler internal server error response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<Error> handlerInternalServerError(InternalServerError exception) {
        return exceptions(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), "Internal server error");
    }

    /**
     * Handler resource not found exception response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handlerResourceNotFoundException(ResourceNotFoundException exception) {
        return exceptions(HttpStatus.NOT_FOUND, exception.getMessage(), "Could not be found");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Error> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        return exceptions(HttpStatus.NOT_FOUND, exception.getMessage(), "User could not be found");
    }

    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public ResponseEntity<Error> handleObjectAlreadyExistsException(ObjectAlreadyExistsException exception) {
        return exceptions(HttpStatus.CONFLICT, exception.getMessage(), "Already Exists");
    }

    private ResponseEntity<Error> exceptions(HttpStatus httpStatus, String details, String message) {
        Error error = Error.builder()
                .httpStatus(httpStatus)
                .details(details)
                .message(message)
                .time(LocalDateTime.now())
                .build();

        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

}


