package com.example.autobank.controller

import com.example.autobank.data.models.Committee
import com.example.autobank.data.user.UserCommitteeResponseBody
import com.example.autobank.service.CommitteeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/committee")
class CommitteeController(
    ) {

    @Autowired
    lateinit var committeeService: CommitteeService

    @GetMapping("/all")
    fun getAllCommittees(): ResponseEntity<List<Committee>> {
        return try {
            val committees = committeeService.getAllCommittees()
            ResponseEntity.ok(committees)
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/user")
    fun getUserAndCommittees(): ResponseEntity<UserCommitteeResponseBody> {
        return try {
            val userandcommittees = committeeService.getUserAndCommittees()
            ResponseEntity.ok(userandcommittees)
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }
}