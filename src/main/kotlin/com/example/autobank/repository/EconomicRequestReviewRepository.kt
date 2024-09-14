package com.example.autobank.repository

import com.example.autobank.data.EconomicRequestReview
import jakarta.transaction.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

@Repository
interface EconomicRequestReviewRepository : JpaRepository<EconomicRequestReview, Int> {
    fun existsByEconomicRequestId(id : Int) : Boolean

    @Transactional
    @Modifying
    @Query("UPDATE EconomicRequestReview e SET e.status = :status WHERE e.economicRequestId = :id")
    fun findEconomicRequestReviewsEconomicRequestIdAndUpdateStatus(id: Int, status: Boolean)

    fun getEconomicRequestReviewByEconomicRequestId(id: Int) : EconomicRequestReview
}
