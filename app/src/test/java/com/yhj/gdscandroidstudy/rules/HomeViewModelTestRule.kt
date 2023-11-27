package com.yhj.gdscandroidstudy.rules

import com.yhj.gdscandroidstudy.domain.TodoRepository
import com.yhj.gdscandroidstudy.ui.home.HomeViewModel
import org.junit.runner.Description

class HomeViewModelTestRule(
    val fakeTodoRepository: TodoRepository,
) : MainDispatcherRule() {

    lateinit var homeViewModel: HomeViewModel

    override fun starting(description: Description) {
        super.starting(description)
        homeViewModel = HomeViewModel(fakeTodoRepository)
    }
}
