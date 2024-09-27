package com.example.autobank.data.receipt

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime


class ReceiptAndReview (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    val id: Int,


    )