package com.example.demo.insectcatalog.controller;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration  // Springの設定クラス
@EnableWebSecurity // Spring SecurityのWebセキュリティサポートを有効化
@EnableMethodSecurity // Spring Securityのメソッドセキュリティサポートを有効化
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // ログイン設定
        // フォームベースのログインを有効にする
        http.formLogin(login -> login
            // ログイン処理のurl
            .loginProcessingUrl("/user/login")
            // ログインページのurl
            .loginPage("/user/login")
            // ログイン時のリダイレクト先
            .defaultSuccessUrl("/insectlist", true)
            // ログイン失敗時のリダイレクト先
            .failureUrl("/user/login")
            //全てのユーザーにアクセス許可
            .permitAll()
        // ログアウト設定
        ).logout(logout -> logout
            // ログアウト時のリダイレクト先
            .logoutSuccessUrl("/user/login")
        // アクセス制御
        ).authorizeHttpRequests(authz -> authz
            // 静的リソース（CSS、画像など）へのアクセスをすべてのユーザーに許可
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            // 登録ページへのアクセスをすべてのユーザーに許可
            .requestMatchers("/user/register").permitAll() 
            // ログインページへのアクセスをすべてのユーザーに許可
            .requestMatchers("/user/login").permitAll() 
            // その他のすべてのリクエストに対して、ユーザーが認証されていることを要求
            .anyRequest().authenticated()
        );
        // これまでの設定をセキュリティの設定を反映し、それを適用する
        return http.build();
    }

    // 入力されたパスワードのハッシュ及びパスワードの比較
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptを使用して入力されたパスワードをハッシュ化し比較
        return new BCryptPasswordEncoder();
    }
}
