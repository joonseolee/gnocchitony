package com.example.autobank.data.models

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "payment")
class Payment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    val id: Int,

    @Column(name = "receipt_id")
    val receiptId: Int,

    @Column(name = "account_number")
    val account_number: String,

)