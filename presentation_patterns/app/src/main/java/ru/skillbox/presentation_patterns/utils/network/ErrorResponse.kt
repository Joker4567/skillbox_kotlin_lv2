package ru.skillbox.presentation_patterns.utils.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ErrorResponse(
        @SerializedName("error")
        val error: String? = null,
        @SerializedName("message")
        val message: String? = null
) : Serializable