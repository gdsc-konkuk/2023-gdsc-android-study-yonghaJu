package com.yhj.gdscandroidstudy.data

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val userNameFlow: Flow<String>
    suspend fun userName(): String
    suspend fun setName(name: String)
}
