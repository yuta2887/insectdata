package com.example.demo.insectcatalog.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsectKindType {
    
    private Integer insect_id; //種族ID
    private String species_name; //カブトムシorクワガタ
}