package com.example.autobank.service;

import com.example.autobank.data.Committee
import com.example.autobank.repository.CommitteeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommitteeService() {

    @Autowired
    lateinit var committeeRepository: CommitteeRepository

    fun getAllCommittees(): List<Committee> {
        return committeeRepository.findAll()
    }
}