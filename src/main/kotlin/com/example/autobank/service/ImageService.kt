package com.example.autobank.service

import com.azure.storage.blob.BlobContainerClient
import com.azure.storage.blob.BlobServiceClientBuilder
import net.coobird.thumbnailator.Thumbnails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*


@Service
class ImageService(
    @Value("\${azure.storage.connection-string}") private val connectionString: String? = null,
    @Value("\${azure.storage.container-name}") private val containerName: String? = null
) {

    val imageResizeWidth = 1000
    val imageResizeHeight = 800

    private val blobContainerClient: BlobContainerClient =
        BlobServiceClientBuilder().connectionString(connectionString).buildClient()
            .getBlobContainerClient(containerName)

    fun uploadImage(image64: String): String {
        val base64Data = image64.substringAfter("base64,")
        val bytearray: ByteArray = Base64.getDecoder().decode(base64Data)
        val resized = resizeImage(bytearray)
        val filename = UUID.randomUUID().toString() + ".jpg"

        val blobClient = blobContainerClient.getBlobClient(filename)
        blobClient.upload(resized.inputStream(), resized.size.toLong())
        println("Uploaded image to $filename")
        return filename
    }

    private fun resizeImage(imageData: ByteArray): ByteArray {
        ByteArrayInputStream(imageData).use { inputStream ->
            ByteArrayOutputStream().use { outputStream ->
                Thumbnails.of(inputStream)
                    .size(imageResizeWidth, imageResizeHeight)
                    .outputFormat("jpg")
                    .toOutputStream(outputStream)

                return outputStream.toByteArray()
            }
        }
    }

    fun downloadImage(fileName: String): String {
        val blobClient = blobContainerClient.getBlobClient(fileName)

        ByteArrayOutputStream().use { outputStream ->
            blobClient.downloadStream(outputStream)

            val imageData: ByteArray = outputStream.toByteArray()
            return Base64.getEncoder().encodeToString(imageData)
        }
    }



}