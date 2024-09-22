package com.example.autobank.service

import org.springframework.stereotype.Service
import com.example.autobank.data.receipt.Receipt
import com.example.autobank.repository.receipt.PaymentRepository
import com.example.autobank.repository.receipt.ReceiptRepository
import org.springframework.beans.factory.annotation.Autowired
import com.example.autobank.repository.receipt.ReceiptInfoViewRepository
import com.example.autobank.data.receipt.*


@Service
class ReceiptAdminService {

    @Autowired
    lateinit var receiptRepository: ReceiptRepository

    @Autowired
    lateinit var receiptInfoViewRepository: ReceiptInfoViewRepository


    fun getAll(from: Int, count: Int): List<ReceiptInfo>? {
        try {
            return receiptInfoViewRepository.findAll().subList(from, from + count)
        } catch (e: Exception) {
            return receiptInfoViewRepository.findAll().subList(0, 5)
        }
    }
/*
    fun getReceipt(id: Int): CompleteReceipt? {
        val receipt = receiptInfoViewRepository.findByReceiptId(id)
        if (receipt == null) {
            return null
        }

        if (receipt.paymentOrCard == "PAYMENT") {
            val payment = receiptRepository.findByReceiptId(id)
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
                payment.paymentAccountNumber,
                "",
                emptyList()
            )
        }

        CompleteReceipt(
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
            receipt.latestReviewCreatedAt
        )



    }*/
}