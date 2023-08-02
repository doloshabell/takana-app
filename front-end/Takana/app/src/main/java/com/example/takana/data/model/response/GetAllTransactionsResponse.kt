package com.example.takana.data.model.response

data class GetAllTransactionsResponse(
    val data: ArrayList<DataAllTransaction>,
    val errors: Any,
    val message: String,
    val status: Boolean,
    val total: Int
)

data class DataAllTransaction(
    val accountAmount: Int,
    val accountName: String,
    val amount: Int,
    val bankAccountNumber: Long,
    val categoryName: String,
    val fromAccountID: Int,
    val fullName: String,
    val note: String,
    val toAccountId: Int,
    val transactionCode: Long,
    val transactionDate: String,
    val transactionId: Int,
    val transactionType: Int,
    val username: String
)