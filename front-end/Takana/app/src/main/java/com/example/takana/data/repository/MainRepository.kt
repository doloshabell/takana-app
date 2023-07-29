package com.example.takana.data.repository

import com.example.takana.data.model.response.GetAllAccountResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface MainRepository {

    @GET("/account/get-all")
    suspend fun getAllAccount(@Header("Authorization") token: String): Response<GetAllAccountResponse>
}