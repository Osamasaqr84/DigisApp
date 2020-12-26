package com.noname.digisapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noname.digisapp.domain.interactors.GetReadingsData
import com.noname.digisapp.models.RandomResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainViewModel(private  val  getReading: GetReadingsData) : ViewModel() {


    val readingsLiveData : MutableLiveData<RandomResponse> = MutableLiveData()
    val readingsErrorLiveData : MutableLiveData<Throwable> = MutableLiveData()
    init {
        retrieveReadings()
    }
    fun retrieveReadings()
    {
        getReading.retrieveReading().
            subscribeOn(Schedulers.io()).
            observeOn(AndroidSchedulers.mainThread()).
            subscribe({
                readingsLiveData.postValue(it)
            },{
                readingsErrorLiveData.postValue(it)
            })
    }
}