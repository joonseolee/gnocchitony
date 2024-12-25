package com.example.autobank.data.models

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "receiptreview")
class ReceiptReview (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    val id: Int,

    @Column(name = "receipt_id")
    val receiptId: Int,

    @Column(name = "status")
    val status: String,

    @Column(name = "comment")
    val comment: String,

    @Column(name = "onlineuser_id")
    var onlineUserId: Int,

    @CreationTimestamp
    @Column(name = "createdat")
    val createdat: LocalDateTime?,
    )
