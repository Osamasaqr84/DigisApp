package com.noname.digisapp.domain.repositry

import com.noname.digisapp.models.RandomResponse
import dagger.Provides
import io.reactivex.Single

interface Repositry {
   fun  retrieveReadingsData() : Single<RandomResponse>
}