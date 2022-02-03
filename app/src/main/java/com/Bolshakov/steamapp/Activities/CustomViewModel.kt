package com.Bolshakov.steamapp.Activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Bolshakov.steamapp.DataBase.DBManagerUsers
import com.Bolshakov.steamapp.Models.Game
import java.util.*
import kotlin.collections.ArrayList

class CustomViewModel : ViewModel() {
    interface CustomInterface{
        fun data(list : ArrayList<Game>)
    }
    private var _readableValue  = MutableLiveData<ArrayList<Game>>().apply {
        getGames(object : CustomInterface{
            override fun data(list: ArrayList<Game>) {
                value = list
            }
        })
    }
    fun getGames(list: CustomInterface){
        val games = DBManagerUsers.readForGamesListDbData()
        list.data(games)
    }
    public val readableValue :LiveData<ArrayList<Game>> = _readableValue
}