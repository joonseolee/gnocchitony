package com.example.autobank.controller

import com.example.autobank.data.Economicrequest
import com.example.autobank.service.AuthenticationService
import com.example.autobank.service.EconomicrequestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController



@RestController
@RequestMapping("/api/economicrequest")
class EconomicrequestController() {

    @Autowired
    lateinit var economicrequestService: EconomicrequestService;

    @Autowired
    lateinit var authenticationService: AuthenticationService;

    @PostMapping("/create")
    fun createEconomicrequest(@RequestBody economicrequest: Economicrequest): ResponseEntity<Economicrequest> {
        println(authenticationService.getUserSub())
        return try {
            val created: Economicrequest = economicrequestService.createEconomicrequest(economicrequest);
            ResponseEntity.ok(created);
        } catch (e: Exception) {
            ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/get/{id}")
    fun getEconomicrequest(@PathVariable id: Int): Economicrequest {
        return economicrequestService.getEconomicrequest(id);
    }

}