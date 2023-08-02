package com.example.takana.data.repository

import com.example.takana.data.model.request.MoneyAccountAddRequest
import com.example.takana.data.model.request.MoneyAccountUpdateRequest
import com.example.takana.data.model.request.TransactionAddRequest
import com.example.takana.data.model.request.TransactionDeleteRequest
import com.example.takana.data.model.request.TransactionUpdateRequest
import com.example.takana.data.model.request.UpdateProfileRequest
import com.example.takana.data.model.response.AddResponse
import com.example.takana.data.model.response.GetAllAccountResponse
import com.example.takana.data.model.response.GetAllTransactionsResponse
import com.example.takana.data.model.response.GetDetailAccountResponse
import com.example.takana.data.model.response.GetDetailProfileResponse
import com.example.takana.data.model.response.GetDetailTransactionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MainRepository {

    //account
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

    @PUT("/account/edit")
    suspend fun updateDataAccount(
        @Header("Authorization") token: String,
        @Body moneyAccountUpdateRequest: MoneyAccountUpdateRequest
    ): Response<AddResponse>

    @PUT("/account/delete")
    suspend fun deleteDataAccount(
        @Header("Authorization") token: String,
        @Body moneyAccountUpdateRequest: MoneyAccountUpdateRequest
    ): Response<AddResponse>

    //transaction
    @GET("/transaction/get-all")
    suspend fun getAllTransaction(
        @Header("Authorization") token: String
    ): Response<GetAllTransactionsResponse>

    @GET("/transaction/get/{id}")
    suspend fun getDetailTransaction(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<GetDetailTransactionResponse>

    @POST("/transaction/new")
    suspend fun addDataTransaction(
        @Header("Authorization") token: String,
        @Body transactionAddRequest: TransactionAddRequest
    ): Response<AddResponse>

    @PUT("/transaction/delete")
    suspend fun deleteDataTransaction(
        @Header("Authorization") token: String,
        @Body transactionDeleteRequest: TransactionDeleteRequest
    ): Response<AddResponse>

    //profile
    @GET("/user/profile/{id}")
    suspend fun getDetailProfile(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<GetDetailProfileResponse>

    @PUT("/user/edit")
    suspend fun updateDataProfile(
        @Header("Authorization") token: String,
        @Body updateProfileRequest: UpdateProfileRequest
    ): Response<AddResponse>

    @PUT("/user/delete")
    suspend fun deleteDataProfile(
        @Header("Authorization") token: String,
        @Body updateProfileRequest: UpdateProfileRequest
    ): Response<AddResponse>
}