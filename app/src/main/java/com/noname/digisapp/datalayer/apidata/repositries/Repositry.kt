package com.noname.digisapp.datalayer.apidata.repositries

import com.noname.digisapp.datalayer.apidata.ReadingModel
import com.noname.digisapp.datalayer.apidata.ServerGateway
import com.noname.digisapp.domain.repositry.Repositry
import com.noname.digisapp.models.RandomResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DigiDataRepositry @Inject constructor(private val server: ServerGateway): Repositry {
    override fun retrieveReadingsData(): Single<RandomResponse> {
        return server.retrieveReadingData()
    }

}


