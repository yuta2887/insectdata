package com.example.demo.insectcatalog.services;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.insectcatalog.beans.User;
import com.example.demo.insectcatalog.mappers.UserMapper;

@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder; 

    // UserMapperとPasswordEncoderをUserServiceに注入
    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // Spring Securityでのユーザー認証に必要な情報をロード
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        
        // ユーザー名に対応するユーザーがデータベースに存在しない場合、例外をスロー
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        // 認証に必要な情報を返す
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities(Collections.emptyList())
            .build();
    }

    // ユーザー名に基づくユーザー情報を取得する
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    // IDに基づくユーザー情報を取得する
    public User findById(int id) {
        return userMapper.findById(id);
    }

    // 新しいユーザーを登録する
    public void registerNewUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password); 
        userMapper.insertNewUser(username, encodedPassword);
        int userId = userMapper.findByUsername(username).getId();
        userMapper.insertUserRole(userId, "ROLE_USER"); 
    }    
}   
