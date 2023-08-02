package com.example.takana.presentation.transaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.takana.data.model.request.TransactionAddRequest
import com.example.takana.data.model.request.TransactionDeleteRequest
import com.example.takana.data.model.request.TransactionUpdateRequest
import com.example.takana.data.model.response.AddResponse
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.GetAllTransactionsResponse
import com.example.takana.data.model.response.GetDetailTransactionResponse
import com.example.takana.data.repository.AllRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    val allRepository = AllRepository()
    val getAllTransactionResult: MutableLiveData<BaseResponse<GetAllTransactionsResponse>> =
        MutableLiveData()
    val getDetailTransactionsResult: MutableLiveData<BaseResponse<GetDetailTransactionResponse>> =
        MutableLiveData()
    val addDataTransactionResult: MutableLiveData<BaseResponse<AddResponse>> = MutableLiveData()
    val deleteDataTransactionResult: MutableLiveData<BaseResponse<AddResponse>> = MutableLiveData()

    fun getAllTransaction(token: String) {
        getAllTransactionResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = allRepository.getAllTransactions("Bearer $token")
                if (response?.body()?.status!!)
                    getAllTransactionResult.value = BaseResponse.Success(response.body())
                else
                    getAllTransactionResult.value = BaseResponse.Error(response.message())
            } catch (ex: Exception) {
                getAllTransactionResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun getDetailTransaction(token: String, id: Long) {
        getDetailTransactionsResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = allRepository.getDetailTransaction("Bearer $token", id)
                if (response?.body()?.status!!)
                    getDetailTransactionsResult.value = BaseResponse.Success(response.body())
                else
                    getDetailTransactionsResult.value = BaseResponse.Error(response.message())
            } catch (ex: Exception) {
                getDetailTransactionsResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun addDataTransaction(
        token: String,
        transactionType: Int,
        categoryId: Int,
        amount: Int,
        note: String,
        fromAccountId: Int,
        toAccountId: Int,
        transactionDate: String
    ) {
        addDataTransactionResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val request = TransactionAddRequest(
                    transactionType,
                    categoryId,
                    amount,
                    note,
                    fromAccountId,
                    toAccountId,
                    transactionDate
                )
                val response = allRepository.addDataTransaction("Bearer $token", request)
                if (response?.body()?.status!!)
                    addDataTransactionResult.value = BaseResponse.Success(response.body())
                else
                    addDataTransactionResult.value = BaseResponse.Error(response.message())
            } catch (ex: Exception) {
                addDataTransactionResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun deleteDataTransaction(
        token: String,
        transactionId: Int,
        transactionCode: Long,
        transactionType: Int,
        transactionDate: String,
        amount: Int,
        fromAccountId: Int,
        toAccountId: Int,
        accountName: String,
        categoryId: Int,
        categoryName: String,
        note: String,
        deletedAt: String,
        userId: Int
    ) {
        deleteDataTransactionResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val request = TransactionDeleteRequest(
                    transactionId,
                    transactionCode,
                    transactionType,
                    transactionDate,
                    amount,
                    fromAccountId,
                    toAccountId,
                    accountName,
                    categoryId,
                    categoryName,
                    note,
                    deletedAt,
                    userId
                )
                val response = allRepository.deleteDataTransaction("Bearer $token", request)
                if (response?.body()?.status!!)
                    addDataTransactionResult.value = BaseResponse.Success(response.body())
                else
                    addDataTransactionResult.value = BaseResponse.Error(response.message())
            } catch (ex: Exception) {
                addDataTransactionResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}