package com.example.autobank.service

import com.example.autobank.data.receipt.*
import com.example.autobank.repository.CardRepository
import com.example.autobank.repository.PaymentRepository
import com.example.autobank.repository.ReceiptRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service

class ReceiptService {

    @Autowired
    lateinit var onlineUserService: OnlineUserService

    @Autowired
    lateinit var receiptRepository: ReceiptRepository

    @Autowired
    lateinit var imageService: ImageService

    @Autowired
    lateinit var attachmentService: AttachmentService

    @Autowired
    lateinit var cardRepository: CardRepository

    @Autowired
    lateinit var paymentRepository: PaymentRepository



    fun createReceipt(receiptRequestBody: ReceiptRequestBody): ReceiptResponseBody {
        val user = onlineUserService.getOnlineUser() ?: throw Exception("User not found")
        val receiptinfo = receiptRequestBody.receipt ?: throw Exception("Receipt not sent")
        receiptinfo.onlineUserId = user.id;

        val storedReceipt = receiptRepository.save(receiptinfo);


        /**
         * Save attachments
         */
        val attachments = receiptRequestBody.attachments
        val attachmentsnames = mutableListOf<String>()
        attachments.forEach { attachment ->
            val imgname = imageService.uploadImage(attachment)
            attachmentService.createAttachment(Attachment(0, storedReceipt.id, imgname))
            attachmentsnames.add(imgname)
        }

        /**
         * Save payment information
         */
        if (receiptRequestBody.receiptPaymentInformation != null && receiptRequestBody.receiptPaymentInformation.usedOnlineCard && receiptRequestBody.receiptPaymentInformation.cardnumber != null && receiptRequestBody.receiptPaymentInformation.cardnumber != "") {
            val card = Card(0, storedReceipt.id, receiptRequestBody.receiptPaymentInformation.cardnumber)
            cardRepository.save(card)
        } else if (receiptRequestBody.receiptPaymentInformation != null && !receiptRequestBody.receiptPaymentInformation.usedOnlineCard && receiptRequestBody.receiptPaymentInformation.accountnumber != null && receiptRequestBody.receiptPaymentInformation.accountnumber != "") {
            val payment = Payment(0, storedReceipt.id, receiptRequestBody.receiptPaymentInformation.accountnumber)
            paymentRepository.save(payment)
        } else {
            throw Exception("Payment information not sent")
        }

        storedReceipt.onlineUserId = null

        val response = ReceiptResponseBody()
        response.receipt = storedReceipt
        response.attachmentnames = attachmentsnames.toTypedArray()
        response.receiptPaymentInformation = receiptRequestBody.receiptPaymentInformation

        return response

    }


}