package com.example.autobank.controller

import com.example.autobank.data.Comitee
import com.example.autobank.service.ComiteeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HalloController(val service: ComiteeService) {

    @GetMapping("/")
    fun hallo(): String {
        service.addComitee("APPKOM");
        val s: List<Comitee> = service.findComitees();



        return s.get(0).name;
    }
}