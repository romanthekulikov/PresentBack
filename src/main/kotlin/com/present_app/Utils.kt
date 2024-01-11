package com.present_app

import kotlinx.serialization.Serializable

object Utils {
    fun getErrorResponse(code: Int, message: String): ErrorResponseModel {
        return ErrorResponseModel(error = code, code_message = message)
    }

    @Serializable
    data class ErrorResponseModel(
        val error: Int,
        val code_message: String
    )
}