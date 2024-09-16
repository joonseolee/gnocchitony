package com.example.autobank.data

import jakarta.persistence.*

@Entity
@Table(name = "committee")
class Committee (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val name: String
)