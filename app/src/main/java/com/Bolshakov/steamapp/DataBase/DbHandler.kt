package com.Bolshakov.steamapp.DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Cоздание бд и таблицы в ней
class DbHandler(context: Context) : SQLiteOpenHelper(context, DBConfig.DATABASE_NAME, null, DBConfig.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DBConfig.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DBConfig.SQL_DELETE_TABLE)
        onCreate(db)
    }
}