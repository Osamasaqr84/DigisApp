package com.noname.digisapp.datalayer.apidata


import com.noname.digisapp.models.RandomResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ServerGateway {

    @GET("random")
    fun retrieveReadingData(): Single<RandomResponse>
}