package com.example.autobank.controller

import com.example.autobank.data.User
import com.example.autobank.service.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class Admin(@Autowired val adminService: AdminService) {
    @GetMapping("/all")
    fun getAdmins(): ResponseEntity<Any>{
        val admins = adminService.getAdmin()
        return ResponseEntity.ok(admins)

    }

    @GetMapping("/login")
    fun checkAdmin(user: User): Boolean {
        val admins = adminService.getAdmin()
        for (admin in admins){
            if (user.id == admin.id){
                return true
            }
        }
        return false
    }

}