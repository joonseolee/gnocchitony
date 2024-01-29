package com.example.autobank.service;

import com.example.autobank.data.Comitee;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
class CommitteeService(val db: JdbcTemplate) {
    fun findCommittees(): List<Comitee> = db.query("select * from committee") { response, _ ->
            Comitee(response.getInt("id"), response.getString("name"))
    }

    fun addCommittee(name: String) = db.update("insert into committee (name) VALUES ('${name}')");

    }
