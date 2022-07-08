package gr.aytn.todoapp.config

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context){

    private var USER_ID = "USER_ID"
    private var TOKEN = "TOKEN"
    private val preferences: SharedPreferences = context.getSharedPreferences("SharedPref",Context.MODE_PRIVATE)


    var token: String
        get() = preferences.getString(TOKEN,"").toString()
        set(value) = preferences.edit().putString(TOKEN, value).apply()

    var user_id: Int
        get() = preferences.getInt(USER_ID,-1)
        set(value) = preferences.edit().putInt(USER_ID, value).apply()
}