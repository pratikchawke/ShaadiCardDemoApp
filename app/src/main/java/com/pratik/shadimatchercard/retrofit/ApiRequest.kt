package com.pratik.shadimatchercard.retrofit
import com.pratik.shadimatchercard.model.PersonsList
import com.pratik.shadimatchercard.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiRequest {

    @GET("/api/?")
    fun getPersonsList(
        @Query("results") results: Int
    ): Call<PersonsList>
}