package com.iti.fiery

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.io.File

internal class SharedPrefsRepo(private val context: Context) {
    private var sharedPreferences: SharedPreferences

    init {
        // Step 1: Create or retrieve the Master Key for encryption/decryption
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        // Step 2: Initialize/open an instance of EncryptedSharedPreferences
        sharedPreferences = EncryptedSharedPreferences.create(
            SP_FILE_NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    internal fun saveString(key: String, value: String) {
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }

    internal fun readString(key: String) = sharedPreferences.getString(key, SP_DEFAULT_VALUE)

    internal fun deleteSharedPreferences() {
        val dir = File(context.filesDir.parent + "/shared_prefs/")
        val children = dir.list()
        for (child in children) {
            // clear each preference file
            context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply()
            //delete the file
            File(dir, child).delete()
        }
    }
}

internal const val SP_DEFAULT_VALUE = "default_value"
internal const val SP_FILE_NAME = "secret_shared_preferences"
internal const val EMAIL = "email"
internal const val PASSWORD = "password"