package com.example.autobank.service

import org.springframework.stereotype.Service
import com.example.autobank.data.receipt.Receipt
import com.example.autobank.repository.receipt.PaymentRepository
import com.example.autobank.repository.receipt.ReceiptRepository
import org.springframework.beans.factory.annotation.Autowired
import com.example.autobank.repository.receipt.ReceiptInfoViewRepository
import com.example.autobank.data.receipt.*
import com.example.autobank.data.ReceiptReviewRequestBody
import org.springframework.data.domain.PageRequest


@Service
class ReceiptAdminService {

    @Autowired
    lateinit var receiptRepository: ReceiptRepository

    @Autowired
    lateinit var receiptInfoViewRepository: ReceiptInfoViewRepository

    @Autowired
    lateinit var receiptService: ReceiptService

    @Autowired
    lateinit var paymentCardService: PaymentCardService

    @Autowired
    lateinit var imageService: ImageService

    @Autowired
    lateinit var attachmentService: AttachmentService

    fun getAll(from: Int, count: Int): List<ReceiptInfo>? {

        val pageable = PageRequest.of(from, count)
        return receiptInfoViewRepository.findAll(pageable).toList()

    }

    fun getReceipt(id: Int): CompleteReceipt? {
        val receipt = receiptInfoViewRepository.findByReceiptId(id)
        if (receipt == null) {
            return null
        }

        return receiptService.getCompleteReceipt(receipt)

    }

    fun createReceiptReview(reviewBody: ReceiptReviewRequestBody) {

    }
}