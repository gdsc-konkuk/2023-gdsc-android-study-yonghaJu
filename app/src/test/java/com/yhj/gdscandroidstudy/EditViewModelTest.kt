package com.yhj.gdscandroidstudy

import com.yhj.gdscandroidstudy.fakes.FakePhotoRepository
import com.yhj.gdscandroidstudy.fakes.FakeUserRepository
import com.yhj.gdscandroidstudy.rules.EditViewModelTestRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class EditViewModelTest {

    @get:Rule
    val editViewModelTestRule = EditViewModelTestRule(
        FakeUserRepository(),
        FakePhotoRepository(),
    )

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `랜덤 사진을 눌렀을 때 랜덤하게 사진 URL이 받아와 지는지`() = runTest {
        with(editViewModelTestRule) {
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                editViewModel.userPhoto.collect()
            }

            // When
            editViewModel.setRandomPhoto()

            // Then
            assertEquals(FakePhotoRepository.RANDOM_URL, editViewModel.userPhoto.value)
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `이름 변경시 뷰모델 name에 적용이 되어야함 `() = runTest {
        with(editViewModelTestRule) {
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                editViewModel.name.collect()
            }

            // When1
            fakeUserRepository.setName("test1")

            // Then1
            assertEquals("test1", editViewModel.name.value)

            // When2
            fakeUserRepository.setName("test2")

            // Then2
            assertEquals("test2", editViewModel.name.value)
        }
    }
}
