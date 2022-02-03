package com.Bolshakov.steamapp.DataBase

import android.provider.BaseColumns

object DBConfigUsers : BaseColumns {
    const val TABLE_NAME = "users_table"
    const val COLUMN_NAME_ID = BaseColumns._ID
    const val COLUMN_NAME_LOGIN = "login"
    const val COLUMN_NAME_NAME = "name"
    const val COLUMN_NAME_SURNAME = "surname"
    const val COLUMN_NAME_PASSWORD = "password"
    const val COLUMN_NAME_EMAIL = "email"
    const val COLUMN_NAME_IMG_URI =  "uri"
    const val COLUMN_NAME_GAMES = "games"

    const val DATABASE_VERSION = 10
    const val DATABASE_NAME = "SteamDb.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_LOGIN TEXT," +
            "$COLUMN_NAME_NAME TEXT," +
            "$COLUMN_NAME_SURNAME TEXT," +
            "$COLUMN_NAME_PASSWORD TEXT," +
            "$COLUMN_NAME_EMAIL TEXT NOT NULL UNIQUE," +
            "$COLUMN_NAME_GAMES TEXT" +
            "$COLUMN_NAME_IMG_URI TEXT)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

}