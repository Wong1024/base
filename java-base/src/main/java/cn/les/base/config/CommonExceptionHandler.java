package cn.les.base.config;

import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.exception.ValidateException;
import cn.les.base.utils.HttpCode;
import cn.les.base.utils.RequestResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<RequestResult> exceptionHandler(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RequestResult.error(HttpCode.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<RequestResult> resourceNotFoundExceptionHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResult.error(HttpCode.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<RequestResult> constraintViolationExceptionHandler(MissingServletRequestParameterException e) {
        String msg = "缺少" + e.getParameterType() + "型参数" + e.getParameterName();
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResult.error(HttpCode.BAD_PARAM, msg));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<RequestResult> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResult.error(HttpCode.BAD_PARAM, e.getMessage()));
    }

    @ExceptionHandler(ValidateException.class)
    @ResponseBody
    public ResponseEntity<RequestResult> validateExceptionHandler(ValidateException e) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResult.error(HttpCode.BAD_PARAM, e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<RequestResult> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResult.error(HttpCode.BAD_PARAM, "请求参数不能为空"));
    }
}
