package com.example.takana.data.repository

import com.example.takana.data.model.request.LoginRequest
import com.example.takana.data.model.request.RegisterRequest
import com.example.takana.data.model.response.LoginResponse
import com.example.takana.data.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthRepository {

    @POST("/user/login")
    suspend fun logInUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @PUT("/user/logout")
    suspend fun logOutUser(@Header("Authorization") token: String): Response<LoginResponse>

    @POST("/user/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

}