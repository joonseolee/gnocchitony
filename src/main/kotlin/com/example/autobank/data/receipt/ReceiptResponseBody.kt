package com.example.autobank.data.receipt

import com.example.autobank.data.models.Receipt


class ReceiptResponseBody {
    var receipt: Receipt? = null
    var receiptPaymentInformation: ReceiptPaymentInformation? = null
    var attachments: Array<String> = arrayOf()
}