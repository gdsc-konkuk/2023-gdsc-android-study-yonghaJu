package com.yhj.gdscandroidstudy.data.photo

import com.yhj.gdscandroidstudy.BuildConfig
import com.yhj.gdscandroidstudy.util.NETWORK
import okhttp3.Interceptor
import okhttp3.Response

class UnSplashInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
            .addQueryParameter(NETWORK.UNSPLASH.CLIENT_ID, BuildConfig.UNSPLASH_ACCESS_KEY)
            .build()
        val request = chain.request().newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}
