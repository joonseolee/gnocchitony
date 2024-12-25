package com.example.autobank.repository.receipt

import com.example.autobank.data.models.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository : JpaRepository<Payment, Int> {
    fun findFirstByReceiptId(id: Int): Payment?


}
