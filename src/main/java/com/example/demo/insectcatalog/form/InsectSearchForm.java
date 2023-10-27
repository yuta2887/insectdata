package com.example.demo.insectcatalog.form;


import lombok.Data;

@Data
public class InsectSearchForm {

    private Integer insect_id; // 種族ID
    private String keyword; // 検索ワード
}
