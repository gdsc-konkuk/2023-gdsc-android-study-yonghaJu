package com.yhj.gdscandroidstudy.rules

import com.yhj.gdscandroidstudy.domain.TodoRepository
import com.yhj.gdscandroidstudy.fakes.FakeTodoRepository
import com.yhj.gdscandroidstudy.fakes.FakeUserRepository
import com.yhj.gdscandroidstudy.ui.mypage.MyPageViewModel
import org.junit.runner.Description

class MyPageViewModelTestRule : MainDispatcherRule() {

    lateinit var myPageViewModel: MyPageViewModel
    lateinit var fakeTodoRepository: TodoRepository
    override fun starting(description: Description) {
        super.starting(description)
        fakeTodoRepository = FakeTodoRepository()
        myPageViewModel = MyPageViewModel(FakeUserRepository(), fakeTodoRepository)
    }
}
