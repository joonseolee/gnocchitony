package com.example.autobank.service;

import com.example.autobank.data.Comitee;
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
class CommitteeService(private val db: JdbcTemplate) {
    fun findCommittees(): List<Comitee> = db.query("select * from comitee") { response, _ ->
            Comitee(response.getInt("id"), response.getString("name"))
    }

    fun addCommittee(name: String) = db.update("insert into comitee (name) VALUES ('${name}')");

    }
