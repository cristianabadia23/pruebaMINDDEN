package com.example.randomuserlist.inferfaces

import com.example.randomuserlist.models.UsersResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceable {
    @GET("api/")
    suspend fun getUserbyResults(@Query("results=") results: Int):UsersResponse
    @GET("api/")
    suspend fun getUsers():UsersResponse
}

object RetrofitServiceFactory{
    fun makeRetrofitService():ApiServiceable{
        return Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiServiceable::class.java)
    }
}