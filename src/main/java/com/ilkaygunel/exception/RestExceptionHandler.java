package com.ilkaygunel.exception;

import com.ilkaygunel.pojo.MemberOperationPojo;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.UndeclaredThrowableException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex) {
        return buildResponseEntity(ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        if (ex instanceof UndeclaredThrowableException) {
            Throwable throwable = ((UndeclaredThrowableException) ex).getUndeclaredThrowable();
            if (throwable instanceof CustomException) {
                return buildResponseEntity((CustomException) throwable);
            }
        }
        return takeInternalErrorResponseEntity(ex);
    }

    private ResponseEntity<Object> buildResponseEntity(CustomException ex) {
        HttpStatus httpStatus = null;
        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
        memberOperationPojo.setResult(ex.getErrorMessage());
        memberOperationPojo.setErrorCode(ex.getErrorCode());
        if (ex.getHttpStatus() != null) {
            httpStatus = ex.getHttpStatus();
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(memberOperationPojo, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return takeInternalErrorResponseEntity(ex);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
        memberOperationPojo.setErrorCode(ErrorCodes.ERROR_10.getErrorCode());
        memberOperationPojo.setResult(" ");

        ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .forEach(s -> memberOperationPojo.setResult(memberOperationPojo.getResult() + " " + s.getDefaultMessage() + "!"));

        return new ResponseEntity<>(memberOperationPojo, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> takeInternalErrorResponseEntity(Exception ex) {
        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
        memberOperationPojo.setErrorCode(ErrorCodes.ERROR_10.getErrorCode());
        memberOperationPojo.setResult(ex.getMessage());
        return new ResponseEntity<>(memberOperationPojo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
