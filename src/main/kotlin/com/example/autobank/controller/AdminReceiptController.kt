package com.example.autobank.controller;

import com.example.autobank.data.receipt.CompleteReceipt
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.example.autobank.data.receipt.Receipt;
import com.example.autobank.service.ReceiptAdminService;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import com.example.autobank.data.receipt.ReceiptInfo
import com.example.autobank.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import com.example.autobank.data.ReceiptReviewRequestBody
import com.example.autobank.data.receipt.ReceiptReview
import com.example.autobank.service.ReceiptReviewService


@RestController
@RequestMapping("/api/admin/receipt")
class AdminReceiptController {

    @Autowired
    lateinit var receiptAdminService: ReceiptAdminService

    @Autowired
    lateinit var authenticationService: AuthenticationService

    @Autowired
    lateinit var receiptReviewService: ReceiptReviewService


    @GetMapping("/all")
    fun getAllReceipts( @RequestParam from: Int, @RequestParam count: Int)
        : ResponseEntity<List<ReceiptInfo>> {
        if (!authenticationService.checkBankomMembership()) {
            return ResponseEntity.status(403).build()
        }

        return ResponseEntity.ok(receiptAdminService.getAll(from, count))
    }

    @GetMapping("/get/{id}")
    fun getReceipt(@PathVariable id: Int): ResponseEntity<CompleteReceipt> {
        if (!authenticationService.checkBankomMembership()) {
            return ResponseEntity.status(403).build()
        }

        return ResponseEntity.ok(receiptAdminService.getReceipt(id))
    }

    @PostMapping("/review")
    fun reviewReceipt(@RequestBody reviewBody: ReceiptReviewRequestBody): ResponseEntity<ReceiptReview> {
        if (!authenticationService.checkBankomMembership()) {
            return ResponseEntity.status(403).build()
        }
        try {
            return ResponseEntity.ok(receiptReviewService.createReceiptReview(reviewBody));
        } catch (e: Exception) {
            println(e)
            return ResponseEntity.badRequest().build()
        }

    }


}
