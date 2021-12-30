package com.msb.dongbao.advice;

import com.baomidou.kaptcha.exception.KaptchaException;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.msb.dongbao.common.base.TokenException;
import com.msb.dongbao.common.base.result.ResultWrapper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.LoginException;

@RestControllerAdvice
public class GlobalExceptionHandle {

    // 除0 报错
    @ExceptionHandler(ArithmeticException.class)
    public ResultWrapper customException(){
        return ResultWrapper.builder().code(301).msg("统一异常").build();
    }

    /**
     * 自定义token异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(TokenException.class)
    public ResultWrapper loginException(Exception e) {
        return ResultWrapper.getSuccessBuilder().msg(e.getMessage()).build();
    }

    @ExceptionHandler(KaptchaException.class)
    public String kcaptchaException(KaptchaException e) {
        if (e instanceof KaptchaTimeoutException) {
            return "超时";
        }else if (e instanceof KaptchaIncorrectException) {
            return "不正确";
        } else if (e instanceof KaptchaNotFoundException) {
            return "没找到";
        }else {
            return "未知错误";
        }
    }

}
