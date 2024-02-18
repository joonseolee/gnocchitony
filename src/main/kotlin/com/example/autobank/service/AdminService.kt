package com.example.autobank.service;

import com.example.autobank.data.User;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;



@Service
public class AdminService(val db: JdbcTemplate) {
    fun getAdmin(): MutableList<User> {
        return db.query("SELECT * FROM onlineuser WHERE admin=1"){
            response, _ -> User(response.getInt("id"), response.getString("email"), response.getString("online_id"), response.getBoolean("admin"))
        }
    }
    

}
