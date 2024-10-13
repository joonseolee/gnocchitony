package com.example.autobank.data.receipt


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

data class CompleteReceipt(
    val receiptId: Int,

    val amount: BigDecimal,

    val receiptName: String,

    val receiptDescription: String,

    val receiptCreatedAt: LocalDateTime,

    val committeeName: String,

    val userFullname: String,

    val paymentOrCard: String,

    val attachmentCount: Int,

    val latestReviewStatus: String?,

    val latestReviewCreatedAt: LocalDateTime?,

    val latestReviewComment: String?,

    val paymentAccountNumber: String,


    val cardCardNumber: String,

    val attachments: List<String>



)
