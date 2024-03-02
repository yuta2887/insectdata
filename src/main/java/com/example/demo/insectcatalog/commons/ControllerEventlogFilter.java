/*package com.example.demo.insectcatalog.commons;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component //このクラスは自動的にインスタンス化され、そのインスタンスはSpringのIoCコンテナ内に保存される
@Aspect //特定のタイミングで実行される @Before("execution　@After("execution
@Slf4j //SLF4Jの実装
public class ControllerEventlogFilter {

    // InsectController内の全てのメソッド実行前に実行される
    @Before("execution(* com.example.demo.insectcatalog..*InsectController.*(..))")
    public void beforeLog(JoinPoint joinPoint){
        // メソッドの実行前に、そのメソッド名と "START"をログに記録します
        log.info(String.format("%s START", joinPoint.toShortString()));
    }

    // InsectController内の全てのメソッド実行後に実行される
    @After("execution(* com.example.demo.insectcatalog..*InsectController.*(..))")
    public void afterLog(JoinPoint joinPoint){
        // メソッドの実行後に、そのメソッド名と "END"をログに記録します
        log.info(String.format("%s END", joinPoint.toShortString()));
    }
    
}
*/
