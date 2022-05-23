package com.app.kisah25nabi.data.network

import com.app.kisah25nabi.data.KisahResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface ApiService {
    @GET("kisahnabi")
    fun getKisahNabi(): Flowable<List<KisahResponse>>
}