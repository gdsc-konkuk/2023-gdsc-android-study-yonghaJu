package com.yhj.gdscandroidstudy.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserRepository(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userInfo")

    private val userNameKey = stringPreferencesKey(USER_NAME)
    val userNameFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[userNameKey] ?: "주용한"
        }

    suspend fun userName(): String = context.dataStore.data
        .map { preferences ->
            preferences[userNameKey] ?: ""
        }.first()

    suspend fun setName(name: String) {
        context.dataStore.edit { settings ->
            settings[userNameKey] = name
        }
    }

    companion object {
        const val USER_NAME = "userName"
    }
}
