package com.noname.digisapp.domain.interactors

import com.noname.digisapp.domain.repositry.Repositry
import com.noname.digisapp.models.RandomResponse
import io.reactivex.Single


class GetReadingsData(private  val repositry: Repositry)
{
    fun  retrieveReading() :Single<RandomResponse>{
        return repositry.retrieveReadingsData()
    }
}