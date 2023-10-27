package com.example.demo.insectcatalog.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.insectcatalog.beans.Insect;
import com.example.demo.insectcatalog.beans.InsectKindType;
import com.example.demo.insectcatalog.form.InsectForm;
import com.example.demo.insectcatalog.form.InsectSearchForm;
import com.example.demo.insectcatalog.services.InsectService;

import lombok.extern.log4j.Log4j2;

@Log4j2 //SLF4Jの実装
@Controller //Spring MVCのコントローラとして動作
public class InsectController {

    private final InsectService insectService;
    // InsectServiceをInsectControllerに注入
    public InsectController(InsectService insectService) {
        this.insectService = insectService;
    }

    // ログインページを表示
    @GetMapping("/login")
    public String login() {
        return "login";  
    }

    // 昆虫リストページを表示
    @GetMapping("/insectlist")
    public String showInsectPage(InsectSearchForm insectSearchForm, Model model) {          
        log.info("Loading insect data: {}", insectSearchForm);
        this.addToModelInsects(insectSearchForm, model);
        this.addToModelInsectKinds(model);
        return "insect_list";
    }

    // 検索結果をリセット
    @GetMapping("/insectlist/reset")
    public String searchReset(InsectSearchForm insectsearch, Model model){     
        this.addToModelInsectKinds(model);
        this.addToModelInsects(insectsearch, model);
        return "insect_list";
    }

    // 更新ページを表示
    @GetMapping("/insectlist/{insect_no}")
    public String showUpdateInsectPage(@PathVariable("insect_no") Integer insect_no,  @ModelAttribute InsectForm insectForm, Model model){
        this.addToModelInsectKinds(model);
        Insect insectno = insectService.getInsectById(insect_no);
        BeanUtils.copyProperties(insectno, insectForm);
        return "update";
    }  
    
    // 情報を更新
    @PostMapping("/insectlist/updatesave")
    public String updateInsect(@Validated @ModelAttribute InsectForm insectForm, BindingResult result){
        if (result.hasErrors()) {
            return "update"; 
        }
        try {
            Insect insect = convertToEntity(insectForm);
            insectService.update(insect);
            return "redirect:/insectlist";   
        } catch (OptimisticLockingFailureException e) {
            result.addError(new ObjectError("global", e.getMessage()));
            return "update";
        }
    }

    // データの削除
    @PostMapping("/insectlist/delete")
    public String deleteInsect(@ModelAttribute InsectForm insectForm, BindingResult result){
        try{
            Insect insect = convertToEntity(insectForm);
            insectService.delete(insect);
            return "redirect:/insectlist";
        }catch(OptimisticLockingFailureException e){
            result.addError(new ObjectError("global", e.getMessage()));
            return "update";
        }
    }

    // データの追加ページの表示
    @GetMapping("/insectlist/add")
    public String showAddInsectPage(InsectForm insectForm, Model model){
        this.addToModelInsectKinds(model);
        return "add";
    }

    // データ追加
    @PostMapping("/insectlist/addsave")
    public String addInsect(@Validated @ModelAttribute InsectForm insectForm, BindingResult result){
        if (result.hasErrors()) {
            return "add"; 
        }    
        try{
            Insect insect = convertToEntity(insectForm);
            insectService.add(insect);
            return "redirect:/insectlist";
        }catch(OptimisticLockingFailureException e){
            result.addError(new ObjectError("global", e.getMessage()));
            return "add";
        }
    }

    // 種類リストの準備
    private void addToModelInsectKinds(Model model) {
        List<InsectKindType> insectKindTypes = insectService.getKindInsect();
        model.addAttribute("insectKindTypes", insectKindTypes);
    }

    // 昆虫リストの準備
    private void addToModelInsects(InsectSearchForm insectSearchForm, Model model) {
        List<Insect> insects = insectService.getInsect(insectSearchForm);
        model.addAttribute("insects", insects);
    }

    // InsectFormの内容をinsectに変換
    public Insect convertToEntity(InsectForm insectForm) {
        Insect insect = new Insect();
        BeanUtils.copyProperties(insectForm, insect);
        return insect;
    }

}