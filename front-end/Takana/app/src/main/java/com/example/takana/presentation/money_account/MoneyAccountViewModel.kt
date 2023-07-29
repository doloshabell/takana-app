package com.example.takana.presentation.money_account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.takana.data.model.request.AddMoneyAccountRequest
import com.example.takana.data.model.response.AddResponse
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.GetAllAccountResponse
import com.example.takana.data.repository.AllRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MoneyAccountViewModel(application: Application) : AndroidViewModel(application) {

    val allRepository = AllRepository()
    val getAllAccountResult: MutableLiveData<BaseResponse<GetAllAccountResponse>> =
        MutableLiveData()
    val addDataMoneyAccountResult: MutableLiveData<BaseResponse<AddResponse>> = MutableLiveData()

    fun getAllAccountMoney(token: String) {
        getAllAccountResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = allRepository.getAllAccount("Bearer $token")
                if (response?.body()?.status!!) {
                    getAllAccountResult.value = BaseResponse.Success(response.body())
                } else
                    getAllAccountResult.value = BaseResponse.Error(response.message())
            } catch (ex: Exception) {
                getAllAccountResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun addAccountMoney(
        token: String,
        accountName: String,
        accountAmount: Long,
        accountTypeId: Int,
        userId: Int,
        bankAccountNumber: Long
    ) {
        addDataMoneyAccountResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val moneyAccountRequest = AddMoneyAccountRequest(
                    accountName, accountAmount, accountTypeId, userId, bankAccountNumber
                )
                val response = allRepository.addAccountMoney("Bearer $token", moneyAccountRequest)
                if (response?.body()?.status!!) {
                    addDataMoneyAccountResult.value = BaseResponse.Success(response.body())
                } else
                    addDataMoneyAccountResult.value = BaseResponse.Error(response.body()?.message)
            } catch (ex: Exception) {
                addDataMoneyAccountResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}