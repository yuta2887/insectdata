package com.example.demo.insectcatalog.commons;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

//全てのcontrollerに共通処理を実装
@ControllerAdvice
public class CommonAdvice {

    //String型の文字列が入力された場合、前後の空白を削除、削除後空文字の場合nullをに変換    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}