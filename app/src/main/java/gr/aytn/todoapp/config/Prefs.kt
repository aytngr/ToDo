package gr.aytn.todoapp.config

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context){

    private var USER_ID = "USER_ID"
    private var TOKEN = "TOKEN"
    private var IMAGE = "IMAGE"
    private var NAME = "NAME"
    private val preferences: SharedPreferences = context.getSharedPreferences("SharedPref",Context.MODE_PRIVATE)


    var token: String
        get() = preferences.getString(TOKEN,"").toString()
        set(value) = preferences.edit().putString(TOKEN, value).apply()

    var name: String
        get() = preferences.getString(NAME,"User").toString()
        set(value) = preferences.edit().putString(NAME, value).apply()

    var image: String
        get() = preferences.getString(IMAGE,"android.resource://gr.aytn.todoapp/drawable/user_default").toString()
        set(value) = preferences.edit().putString(IMAGE, value).apply()

    var user_id: Int
        get() = preferences.getInt(USER_ID,-1)
        set(value) = preferences.edit().putInt(USER_ID, value).apply()
}