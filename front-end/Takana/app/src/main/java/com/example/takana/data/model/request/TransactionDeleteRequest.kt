package com.example.takana.data.model.request

data class TransactionDeleteRequest(
    val transactionId: Int,
    val transactionCode: Long,
    val transactionType: Int,
    val transactionDate: String,
    val amount: Int,
    val fromAccountId: Int,
    val toAccountId: Int,
    val accountName: String,
    val categoryId: Int,
    val categoryName: String,
    val note: String,
    val deletedAt: String,
    val userId: Int
)