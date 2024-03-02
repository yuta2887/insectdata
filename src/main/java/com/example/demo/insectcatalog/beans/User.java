package com.example.demo.insectcatalog.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// データベースのテーブルを示す
@Entity
// ビルダーパターン実装
@Builder
// ゲッター、セッター、toString、equals、hashCodeメソッドを生成
@Data
// 引数なしのコンストラクタを生成、デフォルト値で初期化
@NoArgsConstructor
// すべてのフィールドを引数とするコンストラクタを生成
@AllArgsConstructor
public class User {

   // 主キー設定
   @Id
   // 主キーをどのように生成するか。データを挿入するたびに、主キーの値を自動的に1増やす
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id; //ユーザー識別 データベースの主キー
   private String username; //ユーザー名 ログイン時に使用
   private String password; //パスワード ログイン時に使用
}