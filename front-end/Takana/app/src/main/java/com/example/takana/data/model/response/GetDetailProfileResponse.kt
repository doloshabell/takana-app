package com.example.takana.data.model.response

data class GetDetailProfileResponse(
    val status: Boolean,
    val message: String,
    val errors: Any,
    val data: DataDetailProfileResponse
)

data class DataDetailProfileResponse(
    val address: String,
    val createdAt: String,
    val email: String,
    val fullName: String,
    val gender: String,
    val image: String,
    val password: String,
    val phoneNumber: String,
    val pin: Int,
    val premiumPlanId: Int,
    val premiumStatus: Boolean,
    val status: String,
    val totalAccount: Int,
    val totalAmount: Int,
    val userId: Int,
    val username: String
)