package com.example.autobank.controller

import com.example.autobank.data.user.OnlineUser
import com.example.autobank.service.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminController(@Autowired val adminService: AdminService) {
    @GetMapping("/all")
    fun getAdmins(): ResponseEntity<Any>{
        val admins = adminService.getAdmins()
        return ResponseEntity.ok(admins)
    }

    @GetMapping("/check")
    fun checkAdmin(user: OnlineUser): Boolean {
        return adminService.checkIfAdmin(user)
    }

}