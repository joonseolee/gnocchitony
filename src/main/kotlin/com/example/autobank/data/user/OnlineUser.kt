package com.example.autobank.data.user

import jakarta.persistence.*

@Entity
@Table(name = "onlineuser")
open class OnlineUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open val id: Int,
    @Column(name = "email")
    open val email: String,
    @Column(name = "fullname")
    open val fullname: String,
    @Column(name = "onlineid")
    open val onlineId: String,

    )