package com.Bolshakov.steamapp.Models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class ResponseModel {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null



    override fun toString() : String{
        return "Post{ id $id name $name }"
    }
}