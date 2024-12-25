package com.example.autobank.data.models

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal
import java.time.LocalDateTime


@Entity
@Table(name = "economicrequest")
class Economicrequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    val id: Int,
    @Column(name = "subject")
    val subject: String,
    @Column(name = "purpose")
    val purpose: String,
    @Column(name = "date")
    val date: LocalDateTime,

    @Column(name = "duration")
    val duration: String?,

    @Column(name = "description")
    val description: String,

    @Column(name = "amount", nullable = false)
    val amount: BigDecimal,

    @Column(name = "personcount")
    val personCount: Int?,

    @Column(name = "names")
    val names: String,

    @Column(name = "paymentdescription")
    val paymentDescription: String,

    @Column(name = "otherinformation")
    val otherInformation: String?,

    @CreationTimestamp
    @Column(name = "createdat")
    val createdat: LocalDateTime?,

    @Column(name = "onlineuser_id")
    var onlineUserId: Int?,


    )

