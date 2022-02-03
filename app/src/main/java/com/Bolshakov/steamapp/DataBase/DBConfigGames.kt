package com.Bolshakov.steamapp.DataBase

import android.provider.BaseColumns

object DBConfigGames: BaseColumns {
    const val TABLE_NAME = "game_table"
    const val COLUMN_NAME_ID = BaseColumns._ID
    const val COLUMN_NAME_NAME = "name"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "SteamDb.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_NAME TEXT)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

}