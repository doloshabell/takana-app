package com.example.takana.data.model.response

import java.io.Serializable

data class LoginResponse(
    var status: Boolean,
    var message: String,
    var errors: Any,
    var data: LoginTokenModel
) : Serializable

data class LoginTokenModel(
    var AccessToken: String,
    var RefreshToken: String,
    var AccessUuid: String,
    var RefreshUuid: String,
    var AtExpires: Long = 0L,
    var RtExpires: Long = 0L,
    var Username: String
) : Serializable
