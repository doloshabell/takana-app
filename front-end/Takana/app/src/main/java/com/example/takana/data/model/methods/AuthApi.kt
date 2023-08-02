package com.example.takana.data.model.methods

import com.example.takana.data.ApiAuthClient
import com.example.takana.data.repository.AuthRepository

interface AuthApi {
    companion object {
        fun getApi(): AuthRepository? {
            return ApiAuthClient.client?.create(AuthRepository::class.java)
        }
    }
}