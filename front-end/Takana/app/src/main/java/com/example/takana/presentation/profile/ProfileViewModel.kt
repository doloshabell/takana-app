package com.example.takana.presentation.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.takana.data.model.request.UpdateProfileRequest
import com.example.takana.data.model.response.AddResponse
import com.example.takana.data.repository.AllRepository
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.GetDetailProfileResponse
import com.example.takana.data.model.response.LoginResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    val allRepository = AllRepository()
    val logOutResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()
    val getDetailProfileResult: MutableLiveData<BaseResponse<GetDetailProfileResponse>> =
        MutableLiveData()
    val updateDataProfileResult: MutableLiveData<BaseResponse<AddResponse>> = MutableLiveData()
    val deleteDataProfileResult: MutableLiveData<BaseResponse<AddResponse>> = MutableLiveData()

    fun logOutUser(token: String) {
        logOutResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = allRepository.logOutUser("Bearer $token")
                if (response?.body()?.status!!) {
                    logOutResult.value = BaseResponse.Success(response.body())
                } else {
                    logOutResult.value = BaseResponse.Error(response.body()?.message)
                }
            } catch (ex: Exception) {
                logOutResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun getDetailProfile(token: String, id: Long) {
        getDetailProfileResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = allRepository.getDetailProfile("Bearer $token", id)
                if (response?.body()?.status!!)
                    getDetailProfileResult.value = BaseResponse.Success(response.body())
                else
                    getDetailProfileResult.value = BaseResponse.Error(response.body()?.message)
            } catch (ex: Exception) {
                getDetailProfileResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun updateDataProfile(
        token: String,
        userId: Int,
        email: String,
        username: String,
        password: String,
        fullName: String,
        phoneNumber: String,
        address: String,
        gender: String,
        status: String,
        image: String,
        pin: Int
    ) {
        updateDataProfileResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val request = UpdateProfileRequest(
                    userId,
                    email,
                    username,
                    password,
                    fullName,
                    phoneNumber,
                    address,
                    gender,
                    status,
                    image,
                    pin
                )
                val response = allRepository.updateDataProfile("Bearer $token", request)
                if (response?.body()?.status!!)
                    updateDataProfileResult.value = BaseResponse.Success(response.body())
                else
                    updateDataProfileResult.value = BaseResponse.Error(response.body()?.message)
            } catch (ex: Exception) {
                updateDataProfileResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun deleteDataProfile(
        token: String,
        userId: Int,
        email: String,
        username: String,
        password: String,
        fullName: String,
        phoneNumber: String,
        address: String,
        gender: String,
        status: String,
        image: String,
        pin: Int
    ) {
        deleteDataProfileResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val request = UpdateProfileRequest(
                    userId,
                    email,
                    username,
                    password,
                    fullName,
                    phoneNumber,
                    address,
                    gender,
                    status,
                    image,
                    pin
                )
                val response = allRepository.deleteDataProfile("Bearer $token", request)
                if (response?.body()?.status!!)
                    deleteDataProfileResult.value = BaseResponse.Success(response.body())
                else
                    deleteDataProfileResult.value = BaseResponse.Error(response.body()?.message)
            } catch (ex: Exception) {
                deleteDataProfileResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}