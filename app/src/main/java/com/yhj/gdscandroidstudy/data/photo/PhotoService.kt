package com.yhj.gdscandroidstudy.data.photo

import com.yhj.gdscandroidstudy.util.NETWORK
import retrofit2.Response
import retrofit2.http.GET
interface PhotoService {
    @GET(NETWORK.UNSPLASH.API.GET_RANDOM)
    suspend fun getRandomPhotoUrl(): Response<PhotoUrlResponse>
}
