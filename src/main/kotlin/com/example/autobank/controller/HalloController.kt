package com.example.autobank.controller

import com.example.autobank.data.Comitee
import com.example.autobank.service.CommitteeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HalloController(val service: CommitteeService) {

    @GetMapping("/")
    fun hallo(): String {
        service.addCommittee("APPKOM");
        val s: List<Comitee> = service.findCommittees();



        return s.get(0).name;
    }
}