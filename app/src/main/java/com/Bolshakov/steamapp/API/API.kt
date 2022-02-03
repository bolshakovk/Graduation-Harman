package com.Bolshakov.steamapp.Models

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body

interface API {
    @POST("games")
    open fun requestGame(@Body requestBody: RequestBody.Companion): Call<ResponseModel>
}