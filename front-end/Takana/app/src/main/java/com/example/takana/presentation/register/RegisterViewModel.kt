package com.example.takana.presentation.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.takana.data.repository.AllRepository
import com.example.takana.data.model.request.RegisterRequest
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.RegisterResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    val allRepository = AllRepository()
    val registerResult: MutableLiveData<BaseResponse<RegisterResponse>> = MutableLiveData()

    fun registerUser(
        email: String,
        fullName: String,
        password: String,
        phoneNumber: String,
        userName: String
    ) {
        registerResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val registerRequest = RegisterRequest(
                    email = email,
                    fullName = fullName,
                    password = password,
                    phoneNumber = phoneNumber,
                    username = userName
                )
                val response = allRepository.registerUser(registerRequest = registerRequest)
                if (response?.body()?.status!!) {
                    registerResult.value = BaseResponse.Success(response.body())
                } else {
                    registerResult.value = BaseResponse.Error(response.body()?.message)
                }
            } catch (ex: Exception) {
                registerResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}