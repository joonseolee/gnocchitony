package com.example.autobank.repository.receipt

import com.example.autobank.data.models.ReceiptReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.transaction.annotation.Transactional

@Repository
interface ReceiptReviewRepository : JpaRepository<ReceiptReview, Int> {
    fun findFirstByReceiptId(receiptId: Int): ReceiptReview?

    @Modifying
    @Transactional
    fun deleteByReceiptId(receiptId: Int)

}
