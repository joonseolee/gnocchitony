package com.example.autobank.service

import com.example.autobank.data.Attachment
import com.example.autobank.data.Receipt
import com.example.autobank.data.ReceiptRequestBody
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


    fun createReceipt(receiptRequestBody: ReceiptRequestBody): Receipt {
        val user = onlineUserService.getOnlineUser() ?: throw Exception("User not found")
        val receiptinfo = receiptRequestBody.receipt ?: throw Exception("Receipt not sent")
        receiptinfo.onlineUserId = user.id;

        val storedReceipt = receiptRepository.save(receiptinfo);

        val attachments = receiptRequestBody.attachments
        attachments.forEach { attachment ->
            val imgname = imageService.uploadImage(attachment)
            attachmentService.createAttachment(Attachment(0, storedReceipt.id, imgname))
        }

        storedReceipt.onlineUserId = null
        return storedReceipt

    }


}