package com.example.autobank.repository

import com.example.autobank.data.receipt.Card
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CardRepository : JpaRepository<Card, Int> {
    fun findFirstByReceiptId(id: Int): Card?



}
