package com.example.autobank.data.models

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "attachment")
class Attachment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    val id: Int,

    @Column(name = "receipt_id")
    val receiptId: Int,

    @Column(name = "name")
    val name: String,

    )