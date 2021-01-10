package com.noname.digisapp.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.noname.digisapp.datalayer.apidata.ApiClient
import com.noname.digisapp.datalayer.apidata.ServerGateway

class ViewModelFactory(private val baseContext: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == MainViewModel::class.java) {
            return MainViewModel(getApiService(),baseContext) as T
        }
        throw IllegalArgumentException("Invalid view model class type")
    }

    private fun getApiService(): ServerGateway? {
        return ApiClient.client?.create(ServerGateway::class.java)
    }

}