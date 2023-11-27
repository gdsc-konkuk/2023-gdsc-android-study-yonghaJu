package com.yhj.gdscandroidstudy.rules

import com.yhj.gdscandroidstudy.domain.TodoRepository
import com.yhj.gdscandroidstudy.domain.UserRepository
import com.yhj.gdscandroidstudy.ui.mypage.MyPageViewModel
import org.junit.runner.Description

class MyPageViewModelTestRule(
    private val fakeUserRepository: UserRepository,
    val fakeTodoRepository: TodoRepository,
) : MainDispatcherRule() {

    lateinit var myPageViewModel: MyPageViewModel
    override fun starting(description: Description) {
        super.starting(description)
        myPageViewModel = MyPageViewModel(fakeUserRepository, fakeTodoRepository)
    }
}
