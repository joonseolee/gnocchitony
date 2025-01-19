package com.example.autobank.data.receipt

import com.example.autobank.data.models.Receipt


class ReceiptRequestBody {
    val receipt: Receipt? = null
    val receiptPaymentInformation: ReceiptPaymentInformation? = null
    val attachments: Array<String> = arrayOf()
}