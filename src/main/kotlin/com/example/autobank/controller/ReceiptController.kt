package com.example.autobank.controller

import com.example.autobank.data.receipt.Receipt
import com.example.autobank.data.receipt.ReceiptRequestBody
import com.example.autobank.data.receipt.ReceiptResponseBody
import com.example.autobank.service.ImageService
import com.example.autobank.service.ReceiptService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/receipt")
class ReceiptController() {

    @Autowired
    lateinit var imageService: ImageService;

    @Autowired
    lateinit var receiptService: ReceiptService

    @PostMapping("/create")
    fun createReceipt(@RequestBody receipt: ReceiptRequestBody): ResponseEntity<ReceiptResponseBody> {
        return try {
            val res = receiptService.createReceipt(receipt)
            ResponseEntity.ok(res)
        } catch (e: Exception) {
            println(e)
            ResponseEntity.badRequest().build()
        }

    }

    /* For testing. temporary */
    @GetMapping("/getimage/{id}")
    fun getReceipt(@PathVariable id: String): String {
        return imageService.downloadImage(id)
    }
    /*  */

    @GetMapping("/getall")
    fun getAllReceipts(): ResponseEntity<List<Receipt>> {
        return try {
            val res = receiptService.getAllReceiptsFromUser()
            ResponseEntity.ok(res)
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/get/{id}")
    fun getReceipt(@PathVariable id: Int): ResponseEntity<ReceiptResponseBody> {
        return try {
            val res = receiptService.getReceipt(id)
            ResponseEntity.ok(res)
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }


}