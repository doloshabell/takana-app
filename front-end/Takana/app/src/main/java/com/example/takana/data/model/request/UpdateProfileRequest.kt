package com.example.takana.data.model.request

data class UpdateProfileRequest(
    val userId: Int,
    val email: String,
    val username: String,
    val password: String,
    val fullName: String,
    val phoneNumber: String,
    val address: String,
    val gender: String,
    val status: String,
    val image: String,
    val pin: Int,
)