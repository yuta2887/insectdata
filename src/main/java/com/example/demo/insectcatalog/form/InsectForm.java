package com.example.demo.insectcatalog.form;

import java.time.LocalDate;

import com.example.demo.insectcatalog.beans.InsectKindType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InsectForm {
    
    private Integer insect_no; //1
    
    @Valid
    private InsectKindType insect_id; //種族ID

    private String species_name; //カブトムシorクワガタ
    
    @NotBlank
    private String insect_name; //昆虫の種類　例オオクワガタ　カブトムシ
   
    @Size(max = 20)
    private String insect_area; //産地
    
    @Size(max = 20)
    private String insect_family; //累代
    
    @Size(max = 20)
    private String insect_stock; //何匹いるか
   
    private LocalDate set_day; //産卵セット回収日
    private Integer version; //バージョン
    
}