package com.example.takana.data.model.response

import java.io.Serializable

data class AddResponse(
    val status: Boolean,
    val message: String,
    val errors: Any,
    val data: String
): Serializable
