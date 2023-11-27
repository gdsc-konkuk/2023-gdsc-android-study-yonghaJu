package com.yhj.gdscandroidstudy

import app.cash.turbine.test
import com.yhj.gdscandroidstudy.domain.TodoItem
import com.yhj.gdscandroidstudy.rules.HomeViewModelTestRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val homeViewModelTestRule = HomeViewModelTestRule()

    @Test
    fun `뷰모델 생성 후 더미데이터 3개가 들어가 있어야 함`() = runTest {
        with(homeViewModelTestRule) {
            homeViewModel.todoList.test {
                assertEquals(TodoItem.DUMMY, awaitItem())
            }
        }
    }

    @Test
    fun `2번째 아이탬에 클릭시 클릭 여부 변경`() = runTest {
        with(homeViewModelTestRule) {
            homeViewModel.todoList.test {
                // Given
                val originalItems = awaitItem()

                // When
                homeViewModel.clickItem(originalItems[1])

                // Then
                val clickedItems = awaitItem()
                assertEquals(originalItems[1].isCompleted.not(), clickedItems[1].isCompleted)
            }
        }
    }
}
