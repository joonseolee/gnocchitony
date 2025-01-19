package com.example.autobank.data.models


import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "receipt")
class Receipt(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    val id: Int,

    @Column(name = "amount")
    val amount: Double,

    @Column(name = "committee_id")
    val committee_id: Int,

    @Column(name = "name")
    val name: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "onlineuser_id")
    var onlineUserId: Int?,

    @CreationTimestamp
    @Column(name = "createdat")
    val createdat: LocalDateTime?,

    )