package com.example.autobank.service;

import com.example.autobank.data.User;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;



@Service
public class AdminService(val db: JdbcTemplate) {
    fun getAdmin(): List<User>{
        return db.query("SELECT * FROM onlineuser WHERE admin=1");
    }
}
