package com.example.takana.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetAllAccountResponse(
    @SerializedName("data")
    val data: ArrayList<DataAccount>,
    @SerializedName("errors")
    val errors: Any,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("total")
    val total: Int
) : Serializable

data class DataAccount(
    val accountAmount: Long,
    val accountId: Int,
    val accountName: String,
    val accountTypeName: String,
    val bankAccountNumber: Int,
    val createdAt: String,
    val deletedAt: Any,
    val updatedAt: String,
    val userId: Int
): Serializable
