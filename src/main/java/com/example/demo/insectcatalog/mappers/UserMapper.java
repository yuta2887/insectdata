package com.example.demo.insectcatalog.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.insectcatalog.beans.User;

@Mapper
public interface UserMapper {
    // ユーザー名に基づいてユーザー情報を取得
    public User findByUsername(String username);
    // ユーザーIDに基づいてユーザー情報を取得
    public User findById(int id);
    // 新しいユーザーデータをDBに挿入
    public void insertNewUser(@Param("username") String username, @Param("password") String password);
    // 指定されたユーザー名に対する権限を取得
    public List<String> findAuthoritiesByUsername(String username);
    // ユーザーにロールを割り当てる
    public void insertUserRole(int userId, String roleName);

}




    

