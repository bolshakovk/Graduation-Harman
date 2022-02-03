package com.Bolshakov.steamapp.DataBase

import android.content.ContentValues
import android.util.Log
import com.Bolshakov.steamapp.Models.Game
import com.Bolshakov.steamapp.Models.Person

object DBManagerGames {
    //запись при выборе игры в бд
    fun insertToDb(name: String){
        val values = ContentValues().apply {
            put(DBConfigGames.COLUMN_NAME_NAME, name)
        }
        DBManagerUsers.db?.insert(DBConfigGames.TABLE_NAME, null, values)
    }

    //счесть конкретную пользователя
    fun readDbData(request: String):ArrayList<Game>{
        val dataList = ArrayList<Game>()
        val cursor = DBManagerUsers.db?.query(DBConfigGames.TABLE_NAME,
            arrayOf(DBConfigUsers.COLUMN_NAME_NAME),
            "${DBConfigUsers.COLUMN_NAME_LOGIN} = ?", arrayOf(request),
            null, null, null)
        //тут считывание по курсору
        with(cursor){
            while (this?.moveToNext()!!){
                val dataTextName = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_NAME))
                dataList.add(Game(dataTextName))
                Log.d("TAG", "datatextname: $dataTextName game(datatextname): ${Game(dataTextName)}")
            }
        }
        return dataList
    }
    //счесть все игры получается из бд
    fun readDbData():ArrayList<Game>{
        val dataList = ArrayList<Game>()
        val cursor = DBManagerUsers.db?.query(DBConfigGames.TABLE_NAME, null, null, null, null, null, null)
        //тут считывание по курсору
        with(cursor){
            while (this?.moveToNext()!!){
                val dataTextName = getString(getColumnIndexOrThrow(DBConfigGames.COLUMN_NAME_NAME))
                val dataIntID = getInt(getColumnIndexOrThrow("_ID"))
                dataList.add(Game(dataTextName))
            }
        }
        return dataList
    }
}