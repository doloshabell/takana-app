package com.example.takana.data.model.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email")
    var email: String,
    @SerializedName("fullName")
    var fullName: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("phoneNumber")
    var phoneNumber: String,
    @SerializedName("username")
    var username: String
)
