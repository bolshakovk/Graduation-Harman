package com.Bolshakov.steamapp.Models

import com.Bolshakov.steamapp.API.TwitchAPI

class RequestModel {
    val Client_ID : String = TwitchAPI.client_id
    val Authorisation : String = "Bearer ${TwitchAPI.access_token}"

}