package com.yhj.gdscandroidstudy.domain

class SetRandomPhotoUseCase(
    private val photoRepository: PhotoRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() {
        photoRepository.getRandomPhotoUrl().onSuccess { url ->
            userRepository.setUserPhotoUrl(url)
        }
    }
}
