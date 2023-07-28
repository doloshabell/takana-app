package com.example.takana.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.takana.data.model.UserRepository
import com.example.takana.data.model.request.LoginRequest
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.LoginResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val userRepository = UserRepository()
    val logInResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()

    fun logInUser(userName: String, password: String) {
        logInResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(
                    username = userName,
                    password = password
                )
                val response = userRepository.logInUser(loginRequest = loginRequest)
                if (response?.body()?.status!!) {
                    logInResult.value = BaseResponse.Success(response.body())
                } else {
                    logInResult.value = BaseResponse.Error(response.body()?.message)
                }
            } catch (ex: Exception) {
                logInResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}