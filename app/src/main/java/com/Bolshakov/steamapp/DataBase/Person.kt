package com.Bolshakov.steamapp.DataBase

import kotlin.properties.Delegates

object Person {
    var id by Delegates.notNull<Int>()
    lateinit var name : String
    lateinit var surname: String
    lateinit var password : String
    lateinit var email : String
    lateinit var login : String
}