package com.noname.digisapp.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.noname.digisapp.R
import com.noname.digisapp.domain.interactors.jsonParse
import com.noname.digisapp.presentation.viewmodel.MainViewModel
import com.noname.digisapp.presentation.viewmodel.ViewModelFactory
import dagger.android.AndroidInjection
import java.io.InputStream
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrieveReading()
        AndroidInjection.inject(this)

        viewModel = ViewModelProviders.of(this,viewModelFactory).get(MainViewModel::class.java)
        viewModel.readingsLiveData.observe(this, Observer {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        })


    }


    fun retrieveReading() {
        val jsonString = readJSONFromAsset()
        if (jsonString != null) {
            val jsonParse = fromJson(jsonString)
            Log.d("data", jsonParse.toString())
        }
    }


    private fun fromJson(json: String): jsonParse {
        return Gson().fromJson<jsonParse>(json, jsonParse::class.java)
    }

    private fun readJSONFromAsset(): String? {
        var json: String? = null
        try {
            val inputStream: InputStream = assets.open("json_values.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}
