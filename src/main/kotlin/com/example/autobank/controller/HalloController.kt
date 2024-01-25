package com.example.autobank.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HalloController {

    @GetMapping("/")
    fun hallo(): String {
        return "Hei Appkom. Dette er backenden vår."
    }

}