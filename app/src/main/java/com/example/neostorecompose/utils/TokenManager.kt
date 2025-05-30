package com.example.neostorecompose.utils

import android.content.Context
import javax.inject.Inject


const val SHARED_PREFERENCE_NAME = "NeostorePref"
const val Access_Token = "access_token"

class TokenManager @Inject constructor(private val context: Context) {


    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun addAccessToken(accessToken: String){
        editor.putString(Access_Token, accessToken)
        editor.apply()
    }

    fun getAccessToken(): String?{
        return sharedPreferences.getString(Access_Token, null)
    }

    fun clearSession(){
        editor.remove(Access_Token)
        editor.apply()
    }

}