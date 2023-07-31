package com.example.takana.data.model.response

data class GetDetailTransactionResponse(
    val status: Boolean,
    val message: String,
    val errors: Any,
    val data: DataDetailTransaction
)

data class DataDetailTransaction(
    val accountName: String,
    val amount: Int,
    val categoryId: Int,
    val categoryName: String,
    val deletedAt: Any,
    val fromAccountId: Int,
    val note: String,
    val toAccountId: Int,
    val transactionCode: Long,
    val transactionDate: String,
    val transactionId: Int,
    val transactionType: Int
)