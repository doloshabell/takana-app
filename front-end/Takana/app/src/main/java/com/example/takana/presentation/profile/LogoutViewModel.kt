package com.example.takana.presentation.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.takana.data.model.UserRepository
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.LoginResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class LogoutViewModel(application: Application) : AndroidViewModel(application) {

    val userRepository = UserRepository()
    val logOutResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()

    fun logOutUser(token: String) {
        logOutResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = userRepository.logOutUser("Bearer $token")
                if (response?.code() == 200) {
                    logOutResult.value = BaseResponse.Success(response.body())
                } else {
                    logOutResult.value = BaseResponse.Error(response?.message())
                }
            } catch (ex: Exception) {
                logOutResult.value =BaseResponse.Error(ex.message)
            }
        }
    }
}