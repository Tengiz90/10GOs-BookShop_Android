package com.tengiz.itstepacademy_finalproject_android.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SecureStorage(context: Context) {

    companion object {
        private const val PREFS_FILENAME = "secure_prefs"
        private const val KEY_JWT_TOKEN = "jwt_token"
    }

    // Modern way to create the MasterKey
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context, // context
        PREFS_FILENAME, // file name
        masterKey, // masterKey object
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_JWT_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_JWT_TOKEN, null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove(KEY_JWT_TOKEN).apply()
    }
}