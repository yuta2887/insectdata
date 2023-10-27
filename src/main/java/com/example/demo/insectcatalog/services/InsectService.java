package com.example.demo.insectcatalog.services;

import java.util.List;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.insectcatalog.beans.Insect;
import com.example.demo.insectcatalog.beans.InsectKindType;
import com.example.demo.insectcatalog.form.InsectSearchForm;
import com.example.demo.insectcatalog.mappers.InsectKindMapper;
import com.example.demo.insectcatalog.mappers.InsectMapper;

@Service
public class InsectService {
    
    private final InsectKindMapper insectkindsMapper;
    private final InsectMapper insectMapper;

    // 上記MapperをInsectServiceに注入
    public InsectService(InsectKindMapper insectkindsMapper, InsectMapper insectMapper) {
        this.insectkindsMapper = insectkindsMapper;
        this.insectMapper = insectMapper;
    }

    // 昆虫の種類リストを取得
    public List<InsectKindType> getKindInsect(){
        // 昆虫の種類をすべて取得する
        return insectkindsMapper.selectAll();       
    }

    // 指定された検索条件に基づく昆虫リストを取得
    public List<Insect> getInsect(InsectSearchForm insectSerach){
        // 昆虫の情報を検索条件に合わせて取得する
        return insectMapper.selectAllByInsects(insectSerach);
    }

    // 指定された昆虫番号に関する昆虫情報を取得
    public Insect getInsectById(int insectNo){
        // 昆虫の情報を番号に基づいて取得する
        return insectMapper.select(insectNo);
    }

     // 昆虫情報を更新し、楽観的排他制御の確認を行う
    @Transactional
    public int update(Insect insect){
        // 昆虫の情報を更新する
        int cnt = insectMapper.update(insect);
        if (cnt ==0){
            throw new OptimisticLockingFailureException("楽観的排他エラー");
        }
        if (cnt>1){
            throw new RuntimeException("二つ以上更新されました。");
        }
        return cnt; 
    }

    // 昆虫情報を削除し、楽観的排他制御の確認を行う
    @Transactional
    public int delete(Insect insect){
        // 昆虫の情報を削除する
        int cnt = insectMapper.delete(insect) ;
        if (cnt ==0){
            throw new OptimisticLockingFailureException("エラー");
        }
        if (cnt>1){
            throw new RuntimeException("エラー");
        }
        return cnt; 
    }   

    // 新しい昆虫情報を追加
    @Transactional
    public int add(Insect insect){
        // 新しい昆虫番号を取得する
        Integer insectNo = insectMapper.selectNewInsectNo();
        if(insectNo == null) {
            insectNo = 1;
        }
        insect.setInsect_no(insectNo);    
        // 昆虫情報をDBに追加する
        int cnt = insectMapper.add(insect) ;
        if (cnt ==0){
            throw new RuntimeException("エラー");
        }
        return cnt;  
    } 
}
