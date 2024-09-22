package com.example.autobank.service

import org.springframework.stereotype.Service
import com.example.autobank.data.receipt.Receipt
import com.example.autobank.repository.receipt.PaymentRepository
import com.example.autobank.repository.receipt.ReceiptRepository
import org.springframework.beans.factory.annotation.Autowired
import com.example.autobank.repository.receipt.ReceiptInfoViewRepository
import com.example.autobank.data.receipt.*
import com.example.autobank.data.ReceiptReviewRequestBody


@Service
class ReceiptAdminService {

    @Autowired
    lateinit var receiptRepository: ReceiptRepository

    @Autowired
    lateinit var receiptInfoViewRepository: ReceiptInfoViewRepository

    @Autowired
    lateinit var paymentCardService: PaymentCardService

    @Autowired
    lateinit var imageService: ImageService

    @Autowired
    lateinit var attachmentService: AttachmentService

    fun getAll(from: Int, count: Int): List<ReceiptInfo>? {
        try {
            return receiptInfoViewRepository.findAll().subList(from, from + count)
        } catch (e: Exception) {
            return receiptInfoViewRepository.findAll().subList(0, 5)
        }
    }

    fun getReceipt(id: Int): CompleteReceipt? {
        val receipt = receiptInfoViewRepository.findByReceiptId(id)
        if (receipt == null) {
            return null
        }

        var card: Card = Card(0, 0, "")
        var payment: Payment = Payment(0, 0, "")

        if (receipt.paymentOrCard == "Card") {
            card = paymentCardService.getCardByReceiptId(receipt.receiptId)
        } else if (receipt.paymentOrCard == "Payment") {
            payment = paymentCardService.getPaymentByReceiptId(receipt.receiptId)
        }
        println(receipt.paymentOrCard)

        // Get images
        val attachments = attachmentService.getAttachmentsByReceiptId(receipt.receiptId)
        val images = attachments.map { attachment -> imageService.downloadImage(attachment.name) }

        return CompleteReceipt(
            receipt.receiptId,
            receipt.amount,
            receipt.receiptName,
            receipt.receiptDescription,
            receipt.receiptCreatedAt,
            receipt.committeeName,
            receipt.userFullname,
            receipt.paymentOrCard,
            receipt.attachmentCount,
            receipt.latestReviewStatus,
            receipt.latestReviewCreatedAt,
            payment.account_number,
            card.card_number,
            images
        )

    }

    fun createReceiptReview(reviewBody: ReceiptReviewRequestBody) {

    }
}