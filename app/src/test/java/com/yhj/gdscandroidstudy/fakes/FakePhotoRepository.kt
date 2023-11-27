package com.yhj.gdscandroidstudy.fakes

import com.yhj.gdscandroidstudy.domain.PhotoRepository

class FakePhotoRepository : PhotoRepository {
    override suspend fun getRandomPhotoUrl(): Result<String> {
        return Result.success("random url")
    }

    companion object {
        const val RANDOM_URL = "random url"
    }
}
