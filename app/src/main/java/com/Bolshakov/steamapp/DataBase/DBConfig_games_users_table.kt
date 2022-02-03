package com.Bolshakov.steamapp.DataBase

import android.provider.BaseColumns
//many-to-many
object DBConfig_games_users_table {

    const val TABLE_NAME = "games_users_table"
    const val COLUMN_NAME_games_ID = "id_games"
    const val COLUMN_NAME_users_ID = "id_users"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${COLUMN_NAME_games_ID} FOREIGN KEY  REFERENCES ${DBConfigGames.TABLE_NAME}(${DBConfigGames.COLUMN_NAME_ID})," +
            "${COLUMN_NAME_users_ID} FOREIGN KEY REFERENCES ${DBConfigUsers.TABLE_NAME}(${DBConfigUsers.COLUMN_NAME_ID})"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}