package com.example.autobank.data.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "receipt_info")
class ReceiptInfo(
    @Id
    @Column(name = "receipt_id")
    val receiptId: Int,

    @Column(name = "amount")
    val amount: BigDecimal,

    @Column(name = "receipt_name")
    val receiptName: String,

    @Column(name = "receipt_description")
    val receiptDescription: String,

    @Column(name = "receipt_created_at")
    val receiptCreatedAt: LocalDateTime,

    @Column(name = "committee_name")
    val committeeName: String,

    @Column(name = "user_fullname")
    val userFullname: String,

    @Column(name = "user_id")
    val userId: Int,

    @Column(name = "payment_or_card")
    val paymentOrCard: String,

    @Column(name = "attachment_count")
    val attachmentCount: Int,

    @Column(name = "latest_review_status")
    val latestReviewStatus: String,

    @Column(name = "latest_review_created_at")
    val latestReviewCreatedAt: LocalDateTime,

    @Column(name = "latest_review_comment")
    val latestReviewComment: String,

)
