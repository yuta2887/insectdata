package com.example.demo.insectcatalog.beans;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Insect {

    private Integer insect_no; //番号
    private InsectKindType insect_id; //種族ID
    private String species_name; //カブトムシorクワガタ
    private String insect_name; //昆虫の種類　例オオクワガタ　カブトムシ
    private String insect_area; //産地
    private String insect_family; //累代
    private String insect_stock; //何匹いるか
    private LocalDate set_day; //産卵セット回収日
    private Integer version; //楽観的排他制御
}