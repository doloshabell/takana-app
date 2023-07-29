package com.example.takana.presentation.money_account

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.GetAllAccountResponse
import com.example.takana.data.repository.AllRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MoneyAccountViewModel(application: Application): AndroidViewModel(application) {

    val allRepository = AllRepository()
    val getAllAccountResult: MutableLiveData<BaseResponse<GetAllAccountResponse>> = MutableLiveData()

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
}