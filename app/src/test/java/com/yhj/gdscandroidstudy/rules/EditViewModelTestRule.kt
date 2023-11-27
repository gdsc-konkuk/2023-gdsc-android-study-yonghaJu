package com.yhj.gdscandroidstudy.rules

import com.yhj.gdscandroidstudy.domain.PhotoRepository
import com.yhj.gdscandroidstudy.domain.SetRandomPhotoUseCase
import com.yhj.gdscandroidstudy.domain.UserRepository
import com.yhj.gdscandroidstudy.ui.edit.EditViewModel
import org.junit.runner.Description

class EditViewModelTestRule(
    val fakeUserRepository: UserRepository,
    private val fakePhotoRepository: PhotoRepository,
) : MainDispatcherRule() {

    private lateinit var setRandomPhotoUseCase: SetRandomPhotoUseCase
    lateinit var editViewModel: EditViewModel

    override fun starting(description: Description) {
        super.starting(description)
        setRandomPhotoUseCase = SetRandomPhotoUseCase(fakePhotoRepository, fakeUserRepository)
        editViewModel = EditViewModel(
            fakeUserRepository,
            setRandomPhotoUseCase,
        )
    }
}
