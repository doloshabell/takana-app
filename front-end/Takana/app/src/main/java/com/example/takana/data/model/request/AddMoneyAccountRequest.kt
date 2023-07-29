package com.example.takana.data.model.request

data class AddMoneyAccountRequest(
    val accountName: String,
    val accountAmount: Long,
    val accountTypeId: Int,
    val userId: Int,
    val bankAccountNumber: Long
)