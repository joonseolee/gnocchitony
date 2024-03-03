package com.example.autobank.data

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "economicrequest")
class Economicrequest (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        val id: Int,
        @Column(name = "subject")
        open val subject: String,
        @Column(name = "purpose")
        open val purpose: String,
        @Column(name = "date")
        val date: LocalDateTime?,

        @Column(name = "duration")
        val duration: String?,

        @Column(name = "description")
        val description: String?,

        @Column(name = "amount", nullable = false)
        val amount: BigDecimal,

        @Column(name = "personcount")
        val personCount: Int?,

        @Column(name = "names")
        val names: String?,

        @Column(name = "paymentdescription")
        val paymentDescription: String?,

        @Column(name = "otherinformation")
        val otherInformation: String?,

        @Column(name = "onlineuser_id")
        val onlineUserId: Int?,

)

