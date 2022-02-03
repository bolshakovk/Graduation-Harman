package com.Bolshakov.steamapp.DataBase

import android.content.ContentValues
import com.Bolshakov.steamapp.DataBase.DBManagerUsers.db
import com.Bolshakov.steamapp.Models.Game
import com.Bolshakov.steamapp.Models.Person

object DBManagerUsersGames {
    fun insertGameToPerson(values : ContentValues){
        db?.update(DBConfig_games_users_table.TABLE_NAME, values, DBConfig_games_users_table.COLUMN_NAME_users_ID,
            arrayOf("test")
        )
    }
    fun getGameByPerson(request: String) : ArrayList<Game> {
        var testArray : ArrayList<Game> = ArrayList()
        val cursor = db?.query(DBConfig_games_users_table.TABLE_NAME,
            arrayOf(DBConfig_games_users_table.COLUMN_NAME_games_ID), null,null,null,null,null)
        with(cursor){
            while (this?.moveToNext()!!){
                val dataID = getInt(getColumnIndexOrThrow(DBConfig_games_users_table.COLUMN_NAME_games_ID))
                //testArray.add(Game(dataID))
            }
        }
        return testArray
    }
}