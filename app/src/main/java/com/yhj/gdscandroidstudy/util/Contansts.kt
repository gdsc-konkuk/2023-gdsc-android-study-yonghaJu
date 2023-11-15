package com.yhj.gdscandroidstudy.util

val SUBSCRIPTION_TIMEOUT: Long get() = 5000L

object NETWORK {
    object UNSPLASH {
        const val BASE_URL = "https://api.unsplash.com"

        const val MEDIA_TYPE = "application/json"

        const val CLIENT_ID = "client_id"

        object API {
            const val GET_RANDOM = "/photos/random"
        }
    }
}
