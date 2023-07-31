package com.example.takana.data.model.request

data class MoneyAccountUpdateRequest(
    val accountId: Int,
    val accountName: String,
    val accountAmount: Long,
    val accountTypeId: Int,
    val userId: Long,
    val bankAccountNumber: Int?
)