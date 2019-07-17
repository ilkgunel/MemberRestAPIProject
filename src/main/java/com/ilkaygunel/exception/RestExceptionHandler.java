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

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex) {
        return buildResponseEntity(ex);
    }

    private ResponseEntity<Object> buildResponseEntity(CustomException ex) {
        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
        memberOperationPojo.setResult(ex.getErrorMessage());
        memberOperationPojo.setErrorCode(ex.getErrorCode());
        return new ResponseEntity<>(memberOperationPojo, HttpStatus.BAD_REQUEST);
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
}
