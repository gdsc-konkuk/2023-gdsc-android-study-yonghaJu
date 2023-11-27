package com.yhj.gdscandroidstudy.rules

import com.yhj.gdscandroidstudy.domain.SetRandomPhotoUseCase
import com.yhj.gdscandroidstudy.fakes.FakePhotoRepository
import com.yhj.gdscandroidstudy.fakes.FakeUserRepository
import com.yhj.gdscandroidstudy.ui.edit.EditViewModel
import org.junit.runner.Description

class EditViewModelTestRule : MainDispatcherRule() {
    lateinit var editViewModel: EditViewModel
    lateinit var fakeUserRepository: FakeUserRepository
    override fun starting(description: Description) {
        super.starting(description)

        val fakePhotoRepository = FakePhotoRepository()
        fakeUserRepository = FakeUserRepository()
        val setRandomPhotoUseCase = SetRandomPhotoUseCase(fakePhotoRepository, fakeUserRepository)
        editViewModel = EditViewModel(
            fakeUserRepository,
            setRandomPhotoUseCase,
        )
    }
}
