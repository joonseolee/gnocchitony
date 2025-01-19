package com.example.autobank.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.example.autobank.repository.receipt.CardRepository
import com.example.autobank.repository.receipt.PaymentRepository
import com.example.autobank.data.models.Card
import com.example.autobank.data.models.Payment

@Service
class PaymentCardService {

    @Autowired
    lateinit var paymentRepository: PaymentRepository

    @Autowired
    lateinit var cardRepository: CardRepository

    fun getCardByReceiptId(receiptId: Int): Card {
        val card = cardRepository.findFirstByReceiptId(receiptId)
        if (card == null) {
            throw Exception("Card not found")
        }
        return card

    }

    fun getPaymentByReceiptId(receiptId: Int): Payment {
        val payment = paymentRepository.findFirstByReceiptId(receiptId)
        if (payment == null) {
            throw Exception("Payment not found")
        }
        return payment
    }


}





