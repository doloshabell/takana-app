package com.example.takana.data.model.request

data class TransactionAddRequest(
    val transactionType: Int,
    val categoryId: Int,
    val amount: Int,
    val note: String,
    val fromAccountId: Int,
    val toAccountId: Int,
)