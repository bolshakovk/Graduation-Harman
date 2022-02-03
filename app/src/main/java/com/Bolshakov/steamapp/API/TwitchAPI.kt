package com.Bolshakov.steamapp.API

import android.util.Log
import com.Bolshakov.steamapp.Models.API
import com.Bolshakov.steamapp.Models.ResponseModel
import com.api.igdb.request.TwitchAuthenticator
import okhttp3.*
import java.io.IOException
import okhttp3.FormBody

import org.json.JSONObject
import kotlinx.coroutines.*
import retrofit2.Retrofit

object TwitchAPI {
    val client_id = "1lr1byv94o51bwmndnkz521k146t56"
    val client_secret = "iw8kb7dhzn1sw761k60tb95vxfro1m"
    val oauthURL = "https://id.twitch.tv/oauth2/token"
    val baseURL = "https://api.igdb.com/v4/"
    var access_token: String? = null
    val client = OkHttpClient()



    fun oAuth(): Request {
        val formBody = FormBody.Builder()
            .add("client_id", client_id)
            .add("client_secret", client_secret)
            .add("grant_type", "client_credentials").build()

        return Request.Builder()
            .url(oauthURL)
            .post(formBody)
            .build()
    }

    /*
    suspend fun getGames() = coroutineScope {
        if (access_token ==null){
            launch{
                getAccessTokenClosure { token ->
                    access_token = token
                    call.enqueue(object :Callback<ResponseModel>{
                        override fun onFailure(call: Call, e: IOException) {
                            TODO("Not yet implemented")
                        }

                        override fun onResponse(call: Call, response: Response) {
                            TODO("Not yet implemented")
                        }

                    })
                    Log.d("tag", "1")
                }
            }.join()
            Log.d("tag", "2")
        }
    }*/

    fun getAccessToken() {
        client.newCall(oAuth()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        throw IOException("not successful")
                    }
                    access_token = JSONObject(response.body!!.string()).optString("access_token")
                    Log.d("tag", "token from twitch api: $access_token")
                }
            }
        })
    }

    fun getAccessTokenClosure(callback: (String) -> Unit) {
        client.newCall(oAuth()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        throw IOException("not successfull")
                    }
                    callback(JSONObject(response.body!!.string()).optString("access_token"))
                }
            }
        })
    }
}
