package com.example.autobank.service

import org.springframework.stereotype.Service
import com.example.autobank.repository.receipt.ReceiptRepository
import org.springframework.beans.factory.annotation.Autowired
import com.example.autobank.repository.receipt.ReceiptInfoViewRepository
import com.example.autobank.data.receipt.*
import com.example.autobank.data.ReceiptReviewRequestBody
import com.example.autobank.repository.receipt.specification.ReceiptInfoSpecification
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort


@Service
class ReceiptAdminService {

    @Autowired
    lateinit var receiptRepository: ReceiptRepository

    @Autowired
    lateinit var receiptInfoViewRepository: ReceiptInfoViewRepository

    @Autowired
    lateinit var receiptService: ReceiptService

    @Autowired
    lateinit var paymentCardService: PaymentCardService

    @Autowired
    lateinit var imageService: ImageService

    @Autowired
    lateinit var attachmentService: AttachmentService

    fun getAll(from: Int, count: Int, status: String?, committeeName: String?, sortField: String?, sortOrder: String?): List<ReceiptInfo>? {


        val sort = if (!sortField.isNullOrEmpty()) {
            Sort.by(Sort.Direction.fromString(sortOrder ?: "ASC"), sortField)
        } else {
            Sort.unsorted()
        }

        val pageable = PageRequest.of(from, count, sort)

        return if (status != null || committeeName != null) {
            receiptInfoViewRepository.findAll(
                ReceiptInfoSpecification(status, committeeName), pageable
            ).toList()
        } else {
            receiptInfoViewRepository.findAll(pageable).toList()
        }

    }

    fun getReceipt(id: Int): CompleteReceipt? {
        val receipt = receiptInfoViewRepository.findByReceiptId(id)
        if (receipt == null) {
            return null
        }

        return receiptService.getCompleteReceipt(receipt)

    }


}