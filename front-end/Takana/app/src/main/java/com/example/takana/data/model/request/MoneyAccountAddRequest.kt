package com.example.takana.data.model.request

data class MoneyAccountAddRequest(
    val accountName: String,
    val accountAmount: Long,
    val accountTypeId: Int,
    val userId: Long,
    val bankAccountNumber: String
)