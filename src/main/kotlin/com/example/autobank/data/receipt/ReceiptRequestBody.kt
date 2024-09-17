package com.example.autobank.data.receipt

import com.example.autobank.data.receipt.Receipt
import jakarta.persistence.Entity


class ReceiptRequestBody {
    val receipt: Receipt? = null
    val receiptPaymentInformation: ReceiptPaymentInformation? = null
    val attachments: Array<String> = arrayOf()
}