package com.Bolshakov.steamapp.DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Cоздание бд и таблицы в ней
class DbHandler(context: Context) : SQLiteOpenHelper(context, DBConfigUsers.DATABASE_NAME, null, DBConfigUsers.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DBConfigUsers.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DBConfigUsers.SQL_DELETE_TABLE)
        onCreate(db)
    }
}