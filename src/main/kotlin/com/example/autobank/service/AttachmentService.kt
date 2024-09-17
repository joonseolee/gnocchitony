package com.example.autobank.service

import com.example.autobank.data.receipt.Attachment
import com.example.autobank.repository.AttachmentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class AttachmentService {

    @Autowired
    lateinit var attachmentRepository: AttachmentRepository;

    fun createAttachment(attachment: Attachment): Attachment {
        return attachmentRepository.save(attachment);
    }

}