package com.example.autobank.data.receipt

import com.example.autobank.data.models.ReceiptInfo

class ReceiptListResponseBody(
    val receipts: Array<ReceiptInfo>,
    val total: Long,
)