package com.example.autobank.repository

import com.example.autobank.data.receipt.Card
import com.example.autobank.data.receipt.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository : JpaRepository<Payment, Int> {
    fun findFirstByReceiptId(id: Int): Payment?


}
