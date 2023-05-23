package com.example.imageviewer.model

import com.example.imageviewer.core.Repository
import com.example.imageviewer.utils.ktorHttpClient
import com.example.imageviewer.utils.runBlocking
import io.ktor.client.call.body
import io.ktor.client.request.*

class ImageRepository(
    private val httpsURL: String
) : Repository<MutableList<String>> {

    override fun get(): MutableList<String> {
        return runBlocking {
            val content = ktorHttpClient.get(httpsURL)
            content.body<String>().lines().toMutableList()
        }
    }
}
