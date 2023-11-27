package com.yhj.gdscandroidstudy

import com.yhj.gdscandroidstudy.domain.TodoItem
import com.yhj.gdscandroidstudy.fakes.FakeTodoRepository
import com.yhj.gdscandroidstudy.fakes.FakeUserRepository
import com.yhj.gdscandroidstudy.rules.MyPageViewModelTestRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class MyPageViewModelTest {

    @get:Rule
    val myPageViewModelTestRule = MyPageViewModelTestRule(
        FakeUserRepository(),
        FakeTodoRepository(),
    )

    @Test
    fun `todo 완료시 완료된 투두 개수 증가`() = runTest {
        with(myPageViewModelTestRule) {
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                myPageViewModel.completedTodoNum.collect()
            }

            // Given
            fakeTodoRepository.addTodo(TodoItem(1, "test1", false))
            fakeTodoRepository.addTodo(TodoItem(2, "test2", false))
            fakeTodoRepository.addTodo(TodoItem(3, "test3", true))
            assertEquals(1, myPageViewModel.completedTodoNum.value)

            // When
            fakeTodoRepository.setTodo(TodoItem(1, "test1", true))
            fakeTodoRepository.setTodo(TodoItem(2, "test2", true))

            // Then
            assertEquals(3, myPageViewModel.completedTodoNum.value)
        }
    }
}
