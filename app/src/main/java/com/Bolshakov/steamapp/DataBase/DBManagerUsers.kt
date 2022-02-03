package com.Bolshakov.steamapp.DataBase

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.Bolshakov.steamapp.Models.Game
import com.Bolshakov.steamapp.Models.Person
import kotlinx.coroutines.selects.select
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object DBManagerUsers {
    private var DbHandler : DbHandler? = null
    //создание бд
    var db: SQLiteDatabase? = null

    fun init(context: Context){
        DbHandler = DbHandler(context)
    }
    //хеш пароля
    private fun hashMe(password: String): String? {
        var password = password
        try {
            val md: MessageDigest = MessageDigest.getInstance("SHA-1") //could also be MD5, SHA-256 etc.
            md.reset()
            md.update(password.toByteArray(charset("UTF-8")))
            val resultByte: ByteArray = md.digest()
            password = String.format("%01x", BigInteger(1, resultByte))
        } catch (e: NoSuchAlgorithmException) {
            Log.d("EXEPTION", e.toString())
        } catch (ex: UnsupportedEncodingException) {
            Log.d("EXEPTION", ex.toString())
        }
        return password
    }
    //удаление пользователя
    fun deleteDbData(request : String){
        db?.delete(DBConfigUsers.TABLE_NAME, "login = ?", arrayOf(request))
        db?.delete(DBConfigUsers.TABLE_NAME,
            "${DBConfigUsers.COLUMN_NAME_LOGIN} = ?",
            arrayOf(request)
        )
    }
    //удаление игры
    fun updateDbDataGame(v: ContentValues){
        db?.update(DBConfigUsers.TABLE_NAME,
            v,
            "${DBConfigUsers.COLUMN_NAME_LOGIN} = ?",
            arrayOf(Person.login)
        )
    }
    fun openDb(){
        db = DbHandler?.writableDatabase
    }
    fun testRawQuery(request: String): ArrayList<Game>{
        var testArray  : ArrayList<Game> = ArrayList()
        val cursor =db?.rawQuery("select games from users_table where login = ?", arrayOf(request))
        with(cursor){
            while (this?.moveToNext()!!){
                val dataTextGame = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_GAMES))
                testArray.add(Game(dataTextGame))
            }
        }
        return testArray
    }
    //чтение всех игр персоны не работает чето
    fun readGamesFromPerson(request: String): ArrayList<Game> {
        var testArray  : ArrayList<Game> = ArrayList()
        val cursor = db?.query(DBConfigUsers.TABLE_NAME,
            arrayOf(DBConfigUsers.COLUMN_NAME_GAMES), "name = ?", arrayOf(request),null,null,null)
        with(cursor){
            while (this?.moveToNext()!!){
                val dataTextGame = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_GAMES))
                Person.games.add(Game(dataTextGame))
                testArray.add(Game(dataTextGame))
            }
            for (item in testArray){
                Log.d("test", item.name)
            }

        }
        return testArray
    }
    //запись при регистрации в бд
    fun insertToDb(login: String, name: String,surname: String,password: String, email: String){
        val values = ContentValues().apply {
            put(DBConfigUsers.COLUMN_NAME_LOGIN, login)
            put(DBConfigUsers.COLUMN_NAME_NAME, name)
            put(DBConfigUsers.COLUMN_NAME_SURNAME, surname)
            put(DBConfigUsers.COLUMN_NAME_PASSWORD, hashMe(password))
            put(DBConfigUsers.COLUMN_NAME_EMAIL, email)
        }
        db?.insert(DBConfigUsers.TABLE_NAME, null, values)
    }

    fun readDbData(request: String):ArrayList<Person>{
        val dataList = ArrayList<Person>()
        val cursor = db?.query(DBConfigUsers.TABLE_NAME,
            arrayOf(DBConfigUsers.COLUMN_NAME_LOGIN, DBConfigUsers.COLUMN_NAME_NAME,DBConfigUsers.COLUMN_NAME_EMAIL,
            DBConfigUsers.COLUMN_NAME_SURNAME, DBConfigUsers.COLUMN_NAME_PASSWORD, DBConfigUsers.COLUMN_NAME_ID),
            "login = ?",
            arrayOf(request),
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

    //считывает всех друзей
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

    //считывает все игры друзей
    fun readForGamesListDbData(): ArrayList<Game> {
        var testArray  : ArrayList<Game> = ArrayList()
        val cursor = db?.query(DBConfigUsers.TABLE_NAME,
            arrayOf(DBConfigUsers.COLUMN_NAME_GAMES), null, null,null,null,null)
        with(cursor){
            while (this?.moveToNext()!!){
                val dataTextGame = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_GAMES))
                if (dataTextGame == null){
                    continue
                }
            }
        }
        return testArray
    }

    //пробую взять игры по массиву из персон(персоны берутся норм)
    fun getForGamesByMultyPerson(request: ArrayList<String>) : ArrayList<String>{
        var testArray : ArrayList<String> = ArrayList()
        val cursor: Cursor
        for (item in request){
            val cursor = db?.query(DBConfigUsers.TABLE_NAME, arrayOf(DBConfigUsers.COLUMN_NAME_GAMES), "login = ?",
                arrayOf(item), null, null, null)
            with(cursor){
                while (this?.moveToNext()!!){
                    val dataTextGame = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_GAMES))
                    if (dataTextGame == null){
                        continue
                    }
                }
            }
        }

        return testArray
    }

    //это норм берет игры по персоне
    fun getForGamesListDbDataByPerson(request: String):ArrayList<Game>{
        var testArray : ArrayList<Game> = ArrayList()
        val cursor = db?.query(DBConfigUsers.TABLE_NAME,
            arrayOf(DBConfigUsers.COLUMN_NAME_GAMES), "login = ?", arrayOf(request),null,null,null)
        with(cursor){
            while (this?.moveToNext()!!){
                val dataTextGame = getString(getColumnIndexOrThrow(DBConfigUsers.COLUMN_NAME_GAMES))
                if (dataTextGame == null){
                    continue
                }
                Person.games.add(Game(dataTextGame))
                testArray.add(Game(dataTextGame))
            }
        }
        return Person.games
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

    fun updateDbDataByLoginWithPass(values : ContentValues, password: String){
        db?.update(DBConfigUsers.TABLE_NAME, values, "login = ?",
            arrayOf(Person.login)
        )
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

