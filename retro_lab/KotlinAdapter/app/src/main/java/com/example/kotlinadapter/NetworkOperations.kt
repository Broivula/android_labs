package com.example.kotlinadapter

import android.database.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URL

object NetworkOperations {
    const val URL = "https://en.wikipedia.org/w/"

    interface Service {
        @GET("api.php")
        fun hitCountCheck(@Query("action") action: String,
                          @Query("format") format: String,
                          @Query("list") list: String,
                          @Query("srsearch") srsearch: String): Call<Model.Result>
    }

    private val retrofit = Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build()

    val service = retrofit.create(Service::class.java)

    object Model {
        data class Result(val query: Query)
        data class Query(val searchinfo: SearchInfo)
        data class SearchInfo(val totalhits: Int)
    }

}


