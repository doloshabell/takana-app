package com.example.takana.data.repository

import com.example.takana.data.model.request.AddMoneyAccountRequest
import com.example.takana.data.model.response.AddResponse
import com.example.takana.data.model.response.GetAllAccountResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MainRepository {

    @GET("/account/get-all")
    suspend fun getAllAccount(@Header("Authorization") token: String): Response<GetAllAccountResponse>

    @POST("/account/add")
    suspend fun addAccount(@Header("Authorization") token: String, @Body addMoneyAccountRequest: AddMoneyAccountRequest): Response<AddResponse>
}