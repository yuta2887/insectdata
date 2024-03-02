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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

    @Controller
    @RequiredArgsConstructor
    @Slf4j
    public class InsectController {

        // @RequiredArgsConstructorを使用し、InsectServiceをコンストラクタインジェクション
        private final InsectService insectService;

        // viewとredirectの定数化
        private static final String VIEW_LOGIN = "login";
        private static final String VIEW_INSECT_LIST = "insect_list";
        private static final String VIEW_UPDATE = "update";
        private static final String VIEW_ADD = "add";
        private static final String REDIRECT_INSECT_LIST = "redirect:/insectlist";

        //モデル名を定数化
        private static final String MODEL_ATTRIBUTE_INSECTS = "insect";
        private static final String MODEL_ATTRIBUTE_INSECT_FORM = "insectForm";
        private static final String MODEL_ATTRIBUTE_INSECT_KIND_TYPES = "insectKindType";

        // MODEL_ATTRIBUTE_INSECT_KIND_TYPESにinsectKindType
        @ModelAttribute(MODEL_ATTRIBUTE_INSECT_KIND_TYPES)
        public List<InsectKindType> populateInsectKinds() {
            return insectService.getKindInsect();
        }

        @GetMapping("/login")
        public String login() {
            return VIEW_LOGIN;
        }

        @GetMapping({"/insectlist", "/insectlist/reset"})
        public String showInsectPage(InsectSearchForm insectSearchForm, Model model) {
            List<Insect> insects = insectService.getInsect(insectSearchForm);
            model.addAttribute(MODEL_ATTRIBUTE_INSECTS, insects);
            return VIEW_INSECT_LIST;
        }

        @GetMapping("/insectlist/{insect_no}")
        public String showUpdateInsectPage(@PathVariable("insect_no") Integer insect_no, Model model) {
            Insect insect = insectService.getInsectById(insect_no);
            InsectForm insectForm = new InsectForm();
            BeanUtils.copyProperties(insect, insectForm);
            model.addAttribute(MODEL_ATTRIBUTE_INSECT_FORM, insectForm);
            return VIEW_UPDATE;
        }

        @GetMapping("/insectlist/add")
        public String showAddInsectPage(Model model) {
            model.addAttribute(MODEL_ATTRIBUTE_INSECT_FORM, new InsectForm());
            return VIEW_ADD;
        }

        @PostMapping("/insectlist/add")
        public String addInsect(@Validated @ModelAttribute(MODEL_ATTRIBUTE_INSECT_FORM) InsectForm insectForm, BindingResult result) {
            return processInsect(insectForm, result, null);
        }

        @PostMapping("/insectlist/update/{insect_no}")
        public String updateInsect(@Validated @ModelAttribute(MODEL_ATTRIBUTE_INSECT_FORM) InsectForm insectForm, BindingResult result, @PathVariable("insect_no") Integer insect_no) {
            return processInsect(insectForm, result, insect_no);
        }

        private String processInsect(InsectForm insectForm, BindingResult result, Integer insect_no) {
            if (result.hasErrors()) {
                return (insect_no == null) ? VIEW_ADD : VIEW_UPDATE;
            }
            try {
                Insect insect = convertToEntity(insectForm);
                if (insect_no != null) {
                    insect.setInsect_no(insect_no);
                }
                if (insect_no == null) {
                    insectService.add(insect);
                } else {
                    insectService.update(insect);
                }
                return REDIRECT_INSECT_LIST;
            } catch (OptimisticLockingFailureException e) {
                log.error("Optimistic locking failure: {}", e.getMessage());
                result.addError(new ObjectError("globalError", "処理中にエラーが発生しました。もう一度試してください"));
                return (insect_no == null) ? VIEW_ADD : VIEW_UPDATE;
            }
        }

        @PostMapping("/insectlist/delete")
        public String deleteInsect(@ModelAttribute InsectForm insectForm) {
            Insect insect = convertToEntity(insectForm);
            insectService.delete(insect);
            return REDIRECT_INSECT_LIST;
        }

        private Insect convertToEntity(InsectForm insectForm) {
            Insect insect = new Insect();
            BeanUtils.copyProperties(insectForm, insect);
            return insect;
        }
    }