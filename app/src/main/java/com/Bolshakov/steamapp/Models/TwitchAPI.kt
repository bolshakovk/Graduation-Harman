package com.Bolshakov.steamapp.Models

import android.net.Uri
import java.net.URI


object TwitchAPI {
    val client_id = "1lr1byv94o51bwmndnkz521k146t56"
    val client_secret = "0bc9t9wb3cikvjhi9008hg788w8ddc"
    val baseURL = "https://api.igdb.com/v4"             //https://id.twitch.tv/oauth2/token?
    val access_token = "pvcjeabzcmdtsil6jrrtod8a778snj"


    fun oAuth(): Uri.Builder {
        val builder : Uri.Builder = Uri.Builder()
        builder.scheme(baseURL)
            .appendPath(client_id)
            .appendPath(client_secret)
        return builder
    }
}