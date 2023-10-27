package com.example.demo.insectcatalog.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id; //ユーザー識別 データベースの主キー
   private String username; //ユーザー名 ログイン時に使用
   private String password; //パスワード ログイン時に使用
}