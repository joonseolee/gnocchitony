package com.example.autobank.service

import com.example.autobank.data.receipt.*
import com.example.autobank.repository.receipt.CardRepository
import com.example.autobank.repository.receipt.PaymentRepository
import com.example.autobank.repository.receipt.ReceiptRepository
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
        println(receiptRequestBody.receiptPaymentInformation?.cardnumber != null)
        println(receiptRequestBody.receiptPaymentInformation?.cardnumber != "")
        println(receiptRequestBody.receiptPaymentInformation?.usedOnlineCard)
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
        response.attachments = attachmentsnames.toTypedArray()
        response.receiptPaymentInformation = receiptRequestBody.receiptPaymentInformation

        return response

    }

    fun getAllReceiptsFromUser(): List<Receipt>? {
        val user = onlineUserService.getOnlineUser() ?: throw Exception("User not found")
        val receipts = receiptRepository.findAllByOnlineUserId(user.id)

        for (receipt in receipts) {
            receipt.onlineUserId = null
        }

        return receipts
    }

    fun getReceipt(id: Int): ReceiptResponseBody? {
        val user = onlineUserService.getOnlineUser() ?: throw Exception("User not found")
        val receipt = receiptRepository.findById(id).orElseThrow { Exception("Receipt not found") }
        if (receipt.onlineUserId != user.id) {
            throw Exception("Receipt not found")
        }
        receipt.onlineUserId = null

        val receiptResponseBody = ReceiptResponseBody()
        receiptResponseBody.receipt = receipt

        val attachments = attachmentService.getAttachmentsByReceiptId(receipt.id)
        val images = mutableListOf<String>()
        attachments.forEach { attachment ->
            images.add(imageService.downloadImage(attachment.name))
        }

        receiptResponseBody.attachments = attachments.map { it.name }.toTypedArray()

        val payment = paymentRepository.findFirstByReceiptId(receipt.id)
        if (payment != null) {
            receiptResponseBody.receiptPaymentInformation = ReceiptPaymentInformation("", payment.account_number, false)
        } else {
            val card = cardRepository.findFirstByReceiptId(receipt.id)
            if (card != null) {
                receiptResponseBody.receiptPaymentInformation = ReceiptPaymentInformation(card.card_number, "", true)
            } else {
                throw Exception("Payment information not found")
            }
        }

        return receiptResponseBody





    }


}