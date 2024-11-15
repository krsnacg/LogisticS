package com.example.logistics.util

import android.util.Base64
import org.json.JSONObject

object TokenUtils {

    fun extractEmailFromToken(token: String): String? {
        return try {
            val parts = token.split(".")
            if (parts.size < 2) return null
            val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
            JSONObject(payload).getString("email")
        } catch (e: Exception) {
            null
        }
    }
}