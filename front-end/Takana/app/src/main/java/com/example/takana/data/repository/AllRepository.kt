package com.example.takana.data.repository

import com.example.takana.data.model.methods.AuthApi
import com.example.takana.data.model.methods.MainApi
import com.example.takana.data.model.request.LoginRequest
import com.example.takana.data.model.request.MoneyAccountAddRequest
import com.example.takana.data.model.request.MoneyAccountUpdateRequest
import com.example.takana.data.model.request.RegisterRequest
import com.example.takana.data.model.request.TransactionAddRequest
import com.example.takana.data.model.request.TransactionUpdateRequest
import com.example.takana.data.model.request.UpdateProfileRequest
import com.example.takana.data.model.response.AddResponse
import com.example.takana.data.model.response.GetAllAccountResponse
import com.example.takana.data.model.response.GetAllTransactionsResponse
import com.example.takana.data.model.response.GetDetailAccountResponse
import com.example.takana.data.model.response.GetDetailProfileResponse
import com.example.takana.data.model.response.GetDetailTransactionResponse
import com.example.takana.data.model.response.LoginResponse
import com.example.takana.data.model.response.RegisterResponse
import retrofit2.Response

class AllRepository {

    // auth
    suspend fun logInUser(loginRequest: LoginRequest): Response<LoginResponse>? {
        return AuthApi.getApi()?.logInUser(loginRequest)
    }

    suspend fun logOutUser(token: String): Response<LoginResponse>? {
        return AuthApi.getApi()?.logOutUser(token)
    }

    suspend fun registerUser(registerRequest: RegisterRequest): Response<RegisterResponse>? {
        return AuthApi.getApi()?.registerUser(registerRequest)
    }

    //accounts
    suspend fun getAllAccount(token: String): Response<GetAllAccountResponse>? {
        return MainApi.getApi()?.getAllAccount(token)
    }

    suspend fun addAccountMoney(
        token: String,
        moneyAccountAddRequest: MoneyAccountAddRequest
    ): Response<AddResponse>? {
        return MainApi.getApi()?.addAccount(token, moneyAccountAddRequest)
    }

    suspend fun getDetailAccount(token: String, id: Long): Response<GetDetailAccountResponse>? {
        return MainApi.getApi()?.getDetailAccount(token, id)
    }

    suspend fun updateDataAccount(
        token: String,
        moneyAccountUpdateRequest: MoneyAccountUpdateRequest
    ): Response<AddResponse>? {
        return MainApi.getApi()?.updateDataAccount(token, moneyAccountUpdateRequest)
    }

    suspend fun deleteDataAccount(
        token: String,
        moneyAccountUpdateRequest: MoneyAccountUpdateRequest
    ): Response<AddResponse>? {
        return MainApi.getApi()?.deleteDataAccount(token, moneyAccountUpdateRequest)
    }

    //transactions
    suspend fun getAllTransactions(
        token: String
    ): Response<GetAllTransactionsResponse>? {
        return MainApi.getApi()?.getAllTransaction(token)
    }

    suspend fun getDetailTransaction(
        token: String,
        id: Long
    ): Response<GetDetailTransactionResponse>? {
        return MainApi.getApi()?.getDetailTransaction(token, id)
    }

    suspend fun addDataTransaction(
        token: String,
        transactionAddRequest: TransactionAddRequest
    ): Response<AddResponse>? {
        return MainApi.getApi()?.addDataTransaction(token, transactionAddRequest)
    }

    suspend fun deleteDataTransaction(
        token: String,
        transactionUpdateRequest: TransactionUpdateRequest
    ): Response<AddResponse>? {
        return MainApi.getApi()?.deleteDataTransaction(token, transactionUpdateRequest)
    }

    //profile
    suspend fun getDetailProfile(
        token: String,
        id: Long
    ): Response<GetDetailProfileResponse>? {
        return MainApi.getApi()?.getDetailProfile(token, id)
    }

    suspend fun updateDataProfile(
        token: String,
        updateProfileRequest: UpdateProfileRequest
    ): Response<AddResponse>? {
        return MainApi.getApi()?.updateDataProfile(token, updateProfileRequest)
    }

    suspend fun deleteDataProfile(
        token: String,
        updateProfileRequest: UpdateProfileRequest
    ): Response<AddResponse>? {
        return MainApi.getApi()?.deleteDataProfile(token, updateProfileRequest)
    }
}