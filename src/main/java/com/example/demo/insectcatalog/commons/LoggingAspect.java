package com.example.demo.insectcatalog.commons;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // コントローラー内の全てのメソッド実行前に実行される
    @Before("execution(* com.example.demo.insectcatalog.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.debug("開始: {}", joinPoint.getSignature().toShortString());
    }

    // コントローラー内の全てのメソッド実行後に実行される（正常終了時）
    @AfterReturning("execution(* com.example.demo.insectcatalog.controller.*.*(..))")
    public void logAfterReturning(JoinPoint joinPoint) {
        log.debug("終了: {}", joinPoint.getSignature().toShortString());
    }

    // コントローラー内の全てのメソッド実行後に実行される（例外発生時）
    @AfterThrowing(pointcut = "execution(* com.example.demo.insectcatalog.controller.*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        log.error("例外発生: {} エラー: {}", joinPoint.getSignature().toShortString(), error.getMessage());
    }

    // コントローラー内の全てのメソッド実行後に実行される（全ての終了時）
    @After("execution(* com.example.demo.insectcatalog.controller.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        log.debug("完了: {}", joinPoint.getSignature().toShortString());
    }
}