package com.example.autobank.repository.receipt

import com.example.autobank.data.receipt.ReceiptInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReceiptInfoViewRepository : JpaRepository<ReceiptInfo, Int> {

    fun findByReceiptId(id: Int): ReceiptInfo

    fun findByUserId(pageable: Pageable, id: Int): Page<ReceiptInfo>

}
