package com.Bolshakov.steamapp.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

object DBManager {
    private var DbHandler : DbHandler? = null
    //создание бд
    var db: SQLiteDatabase? = null

    fun init(context: Context){
        DbHandler = DbHandler(context)
    }

    //Запись и считывание бд
    fun openDb(){
        db = DbHandler?.writableDatabase
    }
    //запись при регистрации в бд?
    fun insertToDb(login: String, name: String,surname: String,password: String, email: String){
        val values = ContentValues().apply {
            put(DBConfig.COLUMN_NAME_LOGIN, login)
            put(DBConfig.COLUMN_NAME_NAME, name)
            put(DBConfig.COLUMN_NAME_SURNAME, surname)
            put(DBConfig.COLUMN_NAME_PASSWORD, password)
            put(DBConfig.COLUMN_NAME_EMAIL, email)
        }
        db?.insert(DBConfig.TABLE_NAME, null, values)
    }
    //попробовать в () добавить параметр который считываем
    fun readDbData():ArrayList<Person>{
        val dataList = ArrayList<Person>()
        val cursor = db?.query(DBConfig.TABLE_NAME, null, null, null, null, null, null)
            //тут считывание по курсору
        with(cursor){
            while (this?.moveToNext()!!){
                val dataTextLogin = getString(getColumnIndexOrThrow(DBConfig.COLUMN_NAME_LOGIN))
                val dataTextName = getString(getColumnIndexOrThrow(DBConfig.COLUMN_NAME_NAME))
                val dataTextSurName = getString(getColumnIndexOrThrow(DBConfig.COLUMN_NAME_SURNAME))
                val dataTextPassword = getString(getColumnIndexOrThrow(DBConfig.COLUMN_NAME_PASSWORD))
                val dataTextEmail = getString(getColumnIndexOrThrow(DBConfig.COLUMN_NAME_EMAIL))
                Person.name = dataTextName
                Person.email = dataTextEmail
                Person.password = dataTextPassword
                Person.login = dataTextLogin
                Person.surname = dataTextSurName
                dataList.add(Person)
                Log.d("tag", "person name is ${Person.name}, person email is ${Person.email}, person login is ${Person.login}")
            }
        }
        return dataList
    }
    fun closeDb(){
        DbHandler?.close()
    }
}