package com.example.takana.data.repository

import com.example.takana.data.model.methods.AuthApi
import com.example.takana.data.model.methods.MainApi
import com.example.takana.data.model.request.LoginRequest
import com.example.takana.data.model.request.RegisterRequest
import com.example.takana.data.model.response.GetAllAccountResponse
import com.example.takana.data.model.response.LoginResponse
import com.example.takana.data.model.response.RegisterResponse
import retrofit2.Response

class AllRepository {
    suspend fun logInUser(loginRequest: LoginRequest): Response<LoginResponse>? {
        return  AuthApi.getApi()?.logInUser(loginRequest)
    }

    suspend fun logOutUser(token: String): Response<LoginResponse>? {
        return AuthApi.getApi()?.logOutUser(token)
    }

    suspend fun registerUser(registerRequest: RegisterRequest): Response<RegisterResponse>? {
        return AuthApi.getApi()?.registerUser(registerRequest)
    }

    suspend fun getAllAccount(token: String): Response<GetAllAccountResponse>? {
        return MainApi.getApi()?.getAllAccount(token)
    }
}