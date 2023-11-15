package com.yhj.gdscandroidstudy.data.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.yhj.gdscandroidstudy.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(private val context: Context) : UserRepository {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    private val userNameKey = stringPreferencesKey(USER_NAME)
    override val userNameFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[userNameKey] ?: USER_NAME_DEFAULT
        }

    override suspend fun userName(): String = userNameFlow.first()

    override suspend fun setName(name: String) {
        context.dataStore.edit { settings ->
            settings[userNameKey] = name
        }
    }

    companion object {
        const val DATASTORE_NAME = "userInfo"
        const val USER_NAME = "userName"
        const val USER_NAME_DEFAULT = "주용한"
    }
}
