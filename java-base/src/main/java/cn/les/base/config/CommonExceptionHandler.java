package cn.les.base.config;

import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.exception.ValidateException;
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
    public ResponseEntity<Map<String, Object>> exceptionHandler(Exception e) {
        e.printStackTrace();
        Map<String, Object> map = new HashMap<>();
        map.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> resourceNotFoundExceptionHandler(Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> constraintViolationExceptionHandler(MissingServletRequestParameterException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", "缺少" + e.getParameterType() + "型参数" + e.getParameterName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(ValidateException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> validateExceptionHandler(ValidateException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("error", "请求参数不能为空");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }
}
