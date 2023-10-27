package com.example.demo.insectcatalog.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.insectcatalog.beans.InsectKindType;

@Mapper
public interface InsectKindMapper {

    //DBから昆虫種類リストを取得
    public List<InsectKindType> selectAll();
}