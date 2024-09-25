package com.victorsantos.customer.transaction.application.common.exception;

import com.victorsantos.customer.transaction.application.common.dto.ErrorResponse;
import com.victorsantos.customer.transaction.application.common.dto.ValidationErrorResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getType(), ex.getTitle(), ex.getDetail());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> bindingResultErrors = ex.getBindingResult().getAllErrors();

        for (ObjectError error : bindingResultErrors) {
            FieldError fieldError = (FieldError) error;
            String fieldName = toSnakeCase(fieldError.getField());
            String validationMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMessage);
        }

        var validationErrorResponse = new ValidationErrorResponse(
                "validation_error", "Validation error", "One or more fields are invalid", validationErrors);
        return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private String toSnakeCase(String input) {
        return input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException ex) {
        String title = "Unsupported media type";
        String detail = String.format("The requested media type '%s' is not supported", ex.getContentType());
        ErrorResponse errorResponse = new ErrorResponse("unsupported_media_type_error", title, detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {
        String title = "Bad request";
        String detail = String.format("Param '%s' has an invalid value '%s'", ex.getName(), ex.getValue());
        ErrorResponse errorResponse = new ErrorResponse("bad_request_error", title, detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Exception {} was thrown: ", ex.getClass(), ex);
        String message = "Internal server error";
        ErrorResponse errorResponse = new ErrorResponse("internal_server_error", message, message);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
