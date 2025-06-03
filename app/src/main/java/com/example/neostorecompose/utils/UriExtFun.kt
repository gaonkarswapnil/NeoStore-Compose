package com.example.neostorecompose.utils

import android.content.Context
import android.net.Uri
import android.util.Base64

fun Uri.toBase64(context: Context): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(this)
        val bytes = inputStream?.readBytes()
        inputStream?.close()
        bytes?.let {
            Base64.encodeToString(it, Base64.NO_WRAP)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
