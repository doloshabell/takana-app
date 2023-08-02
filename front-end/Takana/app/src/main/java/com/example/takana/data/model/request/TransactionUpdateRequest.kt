package com.example.takana.data.model.request

data class TransactionUpdateRequest(
    val amount: Int,
    val categoryId: Int,
    val deletedAt: Any,
    val fromAccountId: Int,
    val note: String,
    val toAccountId: Int,
    val transactionCode: Long,
    val transactionDate: String,
    val transactionId: Int,
    val transactionType: Int
)