package com.example.aviatickets.model.network

import com.example.aviatickets.model.entity.Offer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("offer_list")
    fun getOfferList(): Call<List<Offer>>
}

object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://my-json-server.typicode.com/estharossa/fake-api-demo/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
