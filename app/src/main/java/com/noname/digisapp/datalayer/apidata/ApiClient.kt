package com.noname.digisapp.datalayer.apidata

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "http://51.195.89.92:6000/"
    private const val TIMEOUT = 30
    private var retrofit: Retrofit? = null
    val client: Retrofit?
        get() {
            if (retrofit == null) {
                val builder = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                val httpClient = OkHttpClient.Builder()
                    .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)

                builder.client(httpClient.build())
                retrofit = builder.build()
            }
            return retrofit
        }

}