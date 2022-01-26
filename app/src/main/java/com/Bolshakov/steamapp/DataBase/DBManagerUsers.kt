package com.Bolshakov.steamapp.DataBase

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.Bolshakov.steamapp.Models.Person

object DBManagerUsers {
    private var DbHandler : DbHandler? = null
    //создание бд
    var db: SQLiteDatabase? = null

    fun init(context: Context){
        DbHandler = DbHandler(context)
    }

    fun deleteDbData(request : String){
        db?.delete(DBConfigUsers.TABLE_NAME, "login = ?", arrayOf(request))
    }
    //Запись и считывание бд
    fun openDb(){
        db = DbHandler?.writableDatabase
    }
    //запись при регистрации в бд?
    fun insertToDb(login: String, name: String,surname: String,password: String, email: String){
        val values = ContentValues().apply {
            put(DBConfigUsers.COLUMN_NAME_LOGIN, login)
            put(DBConfigUsers.COLUMN_NAME_NAME, name)
            put(DBConfigUsers.COLUMN_NAME_SURNAME, surname)
            put(DBConfigUsers.COLUMN_NAME_PASSWORD, password)
            put(DBConfigUsers.COLUMN_NAME_EMAIL, email)
        }
        db?.insert(DBConfigUsers.TABLE_NAME, null, values)
    }
    fun readDbData(request: String):ArrayList<Person>{
        val dataList = ArrayList<Person>()
        val cursor = db?.query(DBConfigUsers.TABLE_NAME,
            arrayOf(DBConfigUsers.COLUMN_NAME_LOGIN, DBConfigUsers.COLUMN_NAME_NAME,DBConfigUsers.COLUMN_NAME_EMAIL,
            DBConfigUsers.COLUMN_NAME_SURNAME, DBConfigUsers.COLUMN_NAME_PASSWORD, DBConfigUsers.COLUMN_NAME_ID),
            "login = ?", arrayOf(request),
            null, null, null)
        //тут считывание по курсору
        with(cursor){
            while (this?.moveToNext()!!){
                val dataTextLogin = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_LOGIN))
                val dataTextName = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_NAME))
                val dataTextSurName = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_SURNAME))
                val dataTextPassword = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_PASSWORD))
                val dataTextEmail = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_EMAIL))
                val dataIntID = getInt(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_ID))
                Person.id = dataIntID
                Person.name = dataTextName
                Person.email = dataTextEmail
                Person.password = dataTextPassword
                Person.login = dataTextLogin
                Person.surname = dataTextSurName
                dataList.add(Person)
            }
        }
        return dataList
    }
    fun readForFriendListDbData():Array<String>{
        var testArray = emptyArray<String>()
        val cursor = db?.query(DBConfigUsers.TABLE_NAME,
            arrayOf(DBConfigUsers.COLUMN_NAME_LOGIN), null, null,null,null,null)

        with(cursor){
            while (this?.moveToNext()!!){
                val dataTextLogin = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_LOGIN))
                Person.login = dataTextLogin
                testArray += Person.login
            }
        }
        return testArray
    }

    fun readDbData():ArrayList<Person>{
        val dataList = ArrayList<Person>()
        val cursor = db?.query(DBConfigUsers.TABLE_NAME, null, null, null, null, null, null)
            //тут считывание по курсору
        with(cursor){
            while (this?.moveToNext()!!){
                val dataTextLogin = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_LOGIN))
                val dataTextName = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_NAME))
                val dataTextSurName = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_SURNAME))
                val dataTextPassword = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_PASSWORD))
                val dataTextEmail = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_EMAIL))
                val dataIntID = getInt(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_ID))
                Person.id = dataIntID
                Person.name = dataTextName
                Person.email = dataTextEmail
                Person.password = dataTextPassword
                Person.login = dataTextLogin
                Person.surname = dataTextSurName
                dataList.add(Person)
            }
        }
        return dataList
    }
    fun updateDbData(){
        val values = ContentValues()
        values.put(DBConfigUsers.COLUMN_NAME_LOGIN, Person.login)
        values.put(DBConfigUsers.COLUMN_NAME_EMAIL, Person.email)
        values.put(DBConfigUsers.COLUMN_NAME_PASSWORD, Person.password)

        val cursor = db?.update(DBConfigUsers.TABLE_NAME, values, "${DBConfigUsers.COLUMN_NAME_ID}",null)
    }
    fun closeDb(){
        DbHandler?.close()
    }
    @SuppressLint("Range")
    fun logCursor(cursor: Cursor?) {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                var str: String
                do {
                    str = ""
                    for (cn in cursor.columnNames) {
                        str = str + cn + " = " + cursor.getString(cursor.getColumnIndex(cn)) + "; "
                    }
                    Log.d("log", str)
                } while (cursor.moveToNext())
            }
        } else Log.d("log", "Cursor is null")
    }
}

