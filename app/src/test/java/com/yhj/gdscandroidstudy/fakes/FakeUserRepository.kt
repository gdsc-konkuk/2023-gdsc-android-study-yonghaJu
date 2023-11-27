package com.yhj.gdscandroidstudy.fakes

import com.yhj.gdscandroidstudy.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first

class FakeUserRepository : UserRepository {

    private val userNameSharedFlow = MutableSharedFlow<String>()
    override val userNameFlow: Flow<String> get() = userNameSharedFlow

    private val userPhotoUrlSharedFlow = MutableSharedFlow<String>()

    override val userPhotoUrlFlow: Flow<String?> get() = userPhotoUrlSharedFlow

    override suspend fun userName(): String {
        return userNameFlow.first()
    }

    override suspend fun setName(name: String) {
        userNameSharedFlow.emit(name)
    }

    override suspend fun userPhotoUrl(): String? {
        return userPhotoUrlFlow.first()
    }

    override suspend fun setUserPhotoUrl(url: String) {
        userPhotoUrlSharedFlow.emit(url)
    }
}
