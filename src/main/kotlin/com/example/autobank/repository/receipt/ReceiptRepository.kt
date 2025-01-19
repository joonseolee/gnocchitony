package com.example.autobank.repository.receipt

import com.example.autobank.data.models.Receipt
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReceiptRepository : JpaRepository<Receipt, Int> {
    fun findAllByOnlineUserId(onlineUserId: Int): List<Receipt>



}
