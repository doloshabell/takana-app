package com.example.takana.data.repository

import com.example.takana.data.model.request.MoneyAccountAddRequest
import com.example.takana.data.model.response.AddResponse
import com.example.takana.data.model.response.GetAllAccountResponse
import com.example.takana.data.model.response.GetDetailAccountResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MainRepository {

    @GET("/account/get-all")
    suspend fun getAllAccount(
        @Header("Authorization") token: String
    ): Response<GetAllAccountResponse>

    @POST("/account/add")
    suspend fun addAccount(
        @Header("Authorization") token: String,
        @Body moneyAccountAddRequest: MoneyAccountAddRequest
    ): Response<AddResponse>

    @GET("/account/get/{id}")
    suspend fun getDetailAccount(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<GetDetailAccountResponse>
}