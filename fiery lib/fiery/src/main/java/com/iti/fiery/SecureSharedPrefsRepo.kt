package com.iti.fiery

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.io.File


internal class SharedPrefsRepo(private val context: Context) {
    private var sharedPreferences: SharedPreferences

    init {
        // Step 1: Create or retrieve the Master Key for encryption/decryption
        val spec = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
            .build()

        val masterKey = MasterKey.Builder(context)
            .setKeyGenParameterSpec(spec)
            .build()


        // Step 2: Initialize/open an instance of EncryptedSharedPreferences
        sharedPreferences = EncryptedSharedPreferences.create(
            context,
            SP_FILE_NAME,
            masterKey,
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