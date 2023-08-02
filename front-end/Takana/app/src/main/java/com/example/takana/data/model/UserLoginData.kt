package com.example.takana.data.model

import java.io.Serializable

data class UserLoginData(
    var userName: String?,
    var fullName: String?
) : Serializable
