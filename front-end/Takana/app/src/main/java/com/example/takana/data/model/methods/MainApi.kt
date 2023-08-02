package com.example.takana.data.model.methods

import com.example.takana.data.ApiAuthClient
import com.example.takana.data.MainAuthClient
import com.example.takana.data.repository.AuthRepository
import com.example.takana.data.repository.MainRepository

interface MainApi {
    companion object {
        fun getApi(): MainRepository? {
            return MainAuthClient.client?.create(MainRepository::class.java)
        }
    }
}