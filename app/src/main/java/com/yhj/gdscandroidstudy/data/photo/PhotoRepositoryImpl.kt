package com.yhj.gdscandroidstudy.data.photo

import com.yhj.gdscandroidstudy.domain.PhotoRepository

class PhotoRepositoryImpl(private val photoService: PhotoService) : PhotoRepository {
    override suspend fun getRandomPhotoUrl(): Result<String> {
        return kotlin.runCatching {
            val response = photoService.getRandomPhotoUrl()
            response.body()?.urls?.thumb ?: throw Exception(response.message())
        }
    }
}
