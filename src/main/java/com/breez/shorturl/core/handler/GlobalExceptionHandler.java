package com.breez.shorturl.core.handler;


import com.breez.shorturl.exceptions.ShortUrlException;
import com.breez.shorturl.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ShortUrlException.class)
    public R shortUrlException(ShortUrlException e) {
        return R.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(BindException.class)
    public R bindExceptionHandler(BindException e) {
        logger.error(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        return R.error(HttpStatus.BAD_REQUEST.value(), e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        logger.error(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        return R.error(HttpStatus.BAD_REQUEST.value(), e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }
}
