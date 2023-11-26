package com.yhj.gdscandroidstudy.domain

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val userNameFlow: Flow<String>
    suspend fun userName(): String
    suspend fun setName(name: String)
    val userPhotoUrlFlow: Flow<String?>
    suspend fun userPhotoUrl(): String?
    suspend fun setUserPhotoUrl(url: String)
}
