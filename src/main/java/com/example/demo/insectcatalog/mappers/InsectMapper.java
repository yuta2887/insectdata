package com.example.demo.insectcatalog.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.insectcatalog.beans.Insect;
import com.example.demo.insectcatalog.form.InsectSearchForm;

@Mapper
public interface InsectMapper {
    
    // 昆虫リストを条件に応じて検索し、一致する昆虫リストを取得
    public List<Insect> selectAllByInsects(InsectSearchForm insectSerach);
    // insectNoを元に昆虫の詳細情報を取得
    public Insect select(int insectNo);
    // 昆虫データを更新
    public int update(Insect insect);
    // 昆虫データを削除
    public int delete(Insect insect);
    // 新しく昆虫データを作るために"insect_no"を生成
    public Integer selectNewInsectNo();
    // 新しい昆虫データ追加
    public int add(Insect insect);
}
