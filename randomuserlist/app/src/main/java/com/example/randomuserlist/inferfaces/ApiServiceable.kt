package com.example.randomuserlist.inferfaces

import com.example.randomuserlist.models.UsersResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceable {
    @GET("api/")
    suspend fun getUserByResults(@Query("page=") page:Int,
                                 @Query("results=") results: Int):UsersResponse
}

object RetrofitServiceFactory{
    fun makeRetrofitService():ApiServiceable{
        return Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiServiceable::class.java)
    }
}