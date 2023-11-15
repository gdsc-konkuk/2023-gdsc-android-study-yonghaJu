package com.yhj.gdscandroidstudy.domain

interface PhotoRepository {
    suspend fun getRandomPhotoUrl(): Result<String>
}
