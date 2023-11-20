package com.example.news.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class EditingAspect {

    @Before("@annotation(com.example.news.aop.Editable)")
    public void logBefore(JoinPoint joinPoint) {
        log.info(Arrays.toString(joinPoint.getArgs()));

    }
}
