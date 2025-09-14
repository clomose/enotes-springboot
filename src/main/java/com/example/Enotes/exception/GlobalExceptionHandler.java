package com.example.Enotes.exception;

import com.example.Enotes.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        log.error("GlobalExceptionHandler : handleException() : {}",e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(Exception e){
        log.error("GlobalExceptionHandler : handleNullPointerException() : {}",e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(Exception e){
        log.error("GlobalExceptionHandler : handleResourceNotFoundError() : {}",e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.NOT_FOUND);
//        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<?> handleResourceAlreadyExists(ResourceAlreadyExists e){
        log.error("GlobalExceptionHandler : handleResourceAlreadyExists() : {}",e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.CONFLICT);
//        return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e){
        log.error("GlobalExceptionHandler : handleValidationException() : {}",e.getError());
        return CommonUtil.createErrorResponse(e.getError(),HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageConversionException(HttpMessageNotReadableException e){
        log.error("GlobalExceptionHandler : handleHttpMessageConversionException() : {}",e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e){
        log.error("GlobalExceptionHandler : handleFileNotFoundException() : {}",e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.NOT_FOUND);
//        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){
        log.error("GlobalExceptionHandler : handleIllegalArgumentException() : {}",e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SuccessException.class)
    public ResponseEntity<?> handleSuccessException(SuccessException e){
        log.error("GlobalExceptionHandler : handleSuccessException() : {}",e.getMessage());
        return CommonUtil.createBuildResponseMessage(e.getMessage(),HttpStatus.OK);
//        return new ResponseEntity<>(e.getMessage(),HttpStatus.Ok);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e){
        log.error("GlobalExceptionHandler : handleBadCredentialsException() : {}",e.getMessage());
        return CommonUtil.createBuildResponseMessage(e.getMessage(),HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_RREQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e){
        log.error("GlobalExceptionHandler : handleAccessDeniedException() : {}",e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(),HttpStatus.FORBIDDEN);
//        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_RREQUEST);
    }
}
