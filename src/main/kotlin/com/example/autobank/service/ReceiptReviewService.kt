package com.example.autobank.service

import com.example.autobank.data.ReceiptReviewRequestBody
import com.example.autobank.data.models.ReceiptReview
import com.example.autobank.repository.receipt.ReceiptReviewRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReceiptReviewService {

    @Autowired
    lateinit var receiptReviewRepository: ReceiptReviewRepository

    @Autowired
    lateinit var onlineUserService: OnlineUserService

    fun createReceiptReview(receiptReview: ReceiptReviewRequestBody): ReceiptReview {
        val onlineuserid = onlineUserService.getOnlineUser()?.id ?: throw Exception("User not found")
        if (receiptReview.status != "APPROVED" && receiptReview.status != "DENIED") {
            throw Exception("Invalid status")
        }
        if (receiptReviewRepository.findFirstByReceiptId(receiptReview.receiptId) != null) {
            println("Updating review")
            receiptReviewRepository.deleteByReceiptId(receiptReview.receiptId)
        }
        return receiptReviewRepository.save(ReceiptReview(0, receiptReview.receiptId, receiptReview.status, receiptReview.comment, onlineuserid, null))

    }
}