package com.noname.digisapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.noname.digisapp.domain.interactors.GetReadingsData
import com.noname.digisapp.domain.repositry.Repositry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(private val repositry: Repositry) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == MainViewModel::class.java) {
            return MainViewModel(GetReadingsData(repositry)) as T
        }
        throw IllegalArgumentException("Invalid view model class type")
    }
}