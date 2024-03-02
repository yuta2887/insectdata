package com.example.demo.insectcatalog.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public String handleOptimisticLockingFailureException(OptimisticLockingFailureException e, RedirectAttributes redirectAttributes) {
        logger.error("Optimistic Locking Failure: {}", e.getMessage());
        redirectAttributes.addFlashAttribute("error", "別のユーザーが既に更新しています。もう一度試してください。");
        return "redirect:/insectlist";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
        logger.error("An error occurred: {}", e.getMessage());
        model.addAttribute("error", "システムエラーが発生しました。管理者に連絡してください。");
        return "error";
    }
}

