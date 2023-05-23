package com.example.imageviewer.utils

import io.ktor.client.*
import io.ktor.client.request.*

fun isInternetAvailable(): Boolean {
    return runBlocking {
        try {
            ktorHttpClient.head("http://google.com")
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }
}

val ktorHttpClient = HttpClient {}