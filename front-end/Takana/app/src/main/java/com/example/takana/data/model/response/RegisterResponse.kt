package com.example.takana.data.model.response

data class RegisterResponse(
    var status: Boolean,
    var message: String,
    var errors: String,
    var data: String
)
