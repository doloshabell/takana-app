package com.example.takana.presentation.money_account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.takana.data.model.request.MoneyAccountAddRequest
import com.example.takana.data.model.request.MoneyAccountUpdateRequest
import com.example.takana.data.model.response.AddResponse
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.GetAllAccountResponse
import com.example.takana.data.model.response.GetDetailAccountResponse
import com.example.takana.data.repository.AllRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MoneyAccountViewModel(application: Application) : AndroidViewModel(application) {

    val allRepository = AllRepository()
    val getAllAccountResult: MutableLiveData<BaseResponse<GetAllAccountResponse>> =
        MutableLiveData()
    val addDataMoneyAccountResult: MutableLiveData<BaseResponse<AddResponse>> = MutableLiveData()
    val getDetailMoneyAccountResult: MutableLiveData<BaseResponse<GetDetailAccountResponse>> =
        MutableLiveData()
    val updateDataMoneyAccountResult: MutableLiveData<BaseResponse<AddResponse>> = MutableLiveData()
    val deleteDataMoneyAccountResult: MutableLiveData<BaseResponse<AddResponse>> = MutableLiveData()

    fun getAllMoneyAccount(token: String) {
        getAllAccountResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = allRepository.getAllAccount("Bearer $token")
                if (response?.body()?.status!!)
                    getAllAccountResult.value = BaseResponse.Success(response.body())
                else
                    getAllAccountResult.value = BaseResponse.Error(response.message())
            } catch (ex: Exception) {
                getAllAccountResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun addMoneyAccount(
        token: String,
        accountName: String,
        accountAmount: Long,
        accountTypeId: Int,
        userId: Long,
        bankAccountNumber: Int?
    ) {
        addDataMoneyAccountResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val moneyAccountRequest = MoneyAccountAddRequest(
                    accountName, accountAmount, accountTypeId, userId, bankAccountNumber
                )
                val response = allRepository.addAccountMoney("Bearer $token", moneyAccountRequest)
                if (response?.body()?.status!!)
                    addDataMoneyAccountResult.value = BaseResponse.Success(response.body())
                else
                    addDataMoneyAccountResult.value = BaseResponse.Error(response.body()?.message)
            } catch (ex: Exception) {
                addDataMoneyAccountResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun getDetailMoneyAccount(token: String, id: Long) {
        getDetailMoneyAccountResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = allRepository.getDetailAccount("Bearer $token", id)
                if (response?.body()?.status!!)
                    getDetailMoneyAccountResult.value = BaseResponse.Success(response.body())
                else
                    getDetailMoneyAccountResult.value = BaseResponse.Error(response.body()?.message)
            } catch (ex: Exception) {
                addDataMoneyAccountResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun updateDataMoneyAccount(
        token: String,
        accountId: Int,
        accountName: String,
        accountAmount: Long,
        accountTypeId: Int,
        userId: Long,
        bankAccountNumber: Int?
    ) {
        updateDataMoneyAccountResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val request = MoneyAccountUpdateRequest(
                    accountId, accountName, accountAmount, accountTypeId, userId, bankAccountNumber
                )
                val response = allRepository.updateDataAccount("Bearer $token", request)
                if (response?.body()?.status!!)
                    updateDataMoneyAccountResult.value = BaseResponse.Success(response.body())
                else
                    updateDataMoneyAccountResult.value =
                        BaseResponse.Error(response.body()?.message)

            } catch (ex: Exception) {
                updateDataMoneyAccountResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun deleteDataMoneyAccount(
        token: String,
        accountId: Int,
        accountName: String,
        accountAmount: Long,
        accountTypeId: Int,
        userId: Long,
        bankAccountNumber: Int?
    ) {
        deleteDataMoneyAccountResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val request = MoneyAccountUpdateRequest(
                    accountId, accountName, accountAmount, accountTypeId, userId, bankAccountNumber
                )
                val response = allRepository.deleteDataAccount("Bearer $token", request)
                if (response?.body()?.status!!)
                    deleteDataMoneyAccountResult.value = BaseResponse.Success(response.body())
                else
                    deleteDataMoneyAccountResult.value =
                        BaseResponse.Error(response.body()?.message)

            } catch (ex: Exception) {
                deleteDataMoneyAccountResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}