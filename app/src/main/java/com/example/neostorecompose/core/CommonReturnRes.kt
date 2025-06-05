package com.example.neostorecompose.core

import com.example.neostorecompose.utils.UiState
import retrofit2.Response

fun <T> Response<T>.toSafeResponse(): Response<T> {
    return if (this.isSuccessful) {
        Response.success(this.body())
    } else {
        Response.error(this.code(), this.errorBody()!!)
    }
}


suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): UiState<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful && response.body() != null) {
            UiState.Success(response.body()!!)
        } else {
            UiState.Error(response.errorBody()?.string() ?: "Unknown error")
        }
    } catch (e: Exception) {
        UiState.Error(e.message ?: "Unknown error")
    }
}