package com.noname.digisapp.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.noname.digisapp.datalayer.apidata.ServerGateway
import com.noname.digisapp.models.RandomResponse
import com.noname.digisapp.presentation.model.GsonParse
import com.noname.digisapp.presentation.model.ReadingColor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.InputStream
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel constructor(private val server: ServerGateway?, private val context: Context) :
    ViewModel() {


    val readingsLiveData: MutableLiveData<RandomResponse> = MutableLiveData()
    val readingsErrorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    lateinit var jsonParse: GsonParse
    lateinit var disposable: Disposable

    init {
        retrieveColorsFromFile()
    }

    @SuppressLint("CheckResult")
    fun retrieveReadingsFromServer() {
        server?.retrieveReadingData()?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
                setColorsWithData(it)
                //  readingsLiveData.postValue(it)
            }, {
                readingsErrorLiveData.postValue(it)
            })
    }

    private fun setColorsWithData(it: RandomResponse) {
        it.RSRPColor = getMatchedColor(it.RSRP, jsonParse.rsrpArray)
        it.RSRQColor = getMatchedColor(it.RSRQ, jsonParse.rsrqArray)
        it.SINRColor = getMatchedColor(it.SINR, jsonParse.sinrArray)
        it.CurrentTime = getCurrentDateTime()
        readingsLiveData.postValue(it)
    }


    private fun getMatchedColor(
        value: Int,
        colorValues: Array<ReadingColor>
    ): String {
        return colorValues.filter { value <= it.To.toFloat() && value >= it.From.toFloat() }[0].Color
    }

    private fun retrieveColorsFromFile() {
        val jsonString = readJSONFromAsset()
        if (jsonString != null) {
            jsonParse = fromJson(jsonString)
            // replace min, max with values
            jsonParse.rsrpArray.filter { it.From == "Min" }[0].From = Integer.MIN_VALUE.toString()
            jsonParse.rsrpArray.filter { it.To == "Max" }[0].To = Integer.MAX_VALUE.toString()

            jsonParse.rsrqArray.filter { it.From == "Min" }[0].From = Integer.MIN_VALUE.toString()
            jsonParse.rsrqArray.filter { it.To == "Max" }[0].To = Integer.MAX_VALUE.toString()

            jsonParse.sinrArray.filter { it.From == "Min" }[0].From = Integer.MIN_VALUE.toString()
            jsonParse.sinrArray.filter { it.To == "Max" }[0].To = Integer.MAX_VALUE.toString()
            Log.d("data", jsonParse.toString())

            disposable = Observable.interval(2, TimeUnit.SECONDS, Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe { retrieveReadingsFromServer() }

        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }


    private fun getCurrentDateTime(): Double {
        val date = Calendar.getInstance().time
        val minutes = date.minutes
        val secs = date.seconds
        val time = "$minutes.$secs".toDouble()
        return time

    }

    private fun fromJson(json: String): GsonParse {
        return Gson().fromJson<GsonParse>(json, GsonParse::class.java)
    }

    private fun readJSONFromAsset(): String? {
        var json: String? = null
        try {
            val inputStream: InputStream = context.assets.open("json_values.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }

}