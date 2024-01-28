package com.example.autobank.service;

import com.example.autobank.data.Comitee;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
class ComiteeService(val db: JdbcTemplate) {
    fun findComitees(): List<Comitee> = db.query("select * from comitee") { response, _ ->
            Comitee(response.getInt("id"), response.getString("name"))
    }

    fun addComitee(name: String) = db.update("insert into comitee (name) VALUES ('${name}')");

    }
