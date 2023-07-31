package com.example.takana.data.model.response

data class GetDetailAccountResponse(
    val status: Boolean,
    val message: String,
    val errors: Any,
    val data: Data
)

data class Data(
    val accountAmount: Int,
    val accountId: Int,
    val accountName: String,
    val accountTypeName: String,
    val bankAccountNumber: Int,
    val createdAt: String,
    val deletedAt: Any,
    val updatedAt: String,
    val userId: Int
)