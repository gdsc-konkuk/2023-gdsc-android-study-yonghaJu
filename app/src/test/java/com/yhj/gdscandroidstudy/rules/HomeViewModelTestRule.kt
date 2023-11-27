package com.yhj.gdscandroidstudy.rules

import com.yhj.gdscandroidstudy.fakes.FakeTodoRepository
import com.yhj.gdscandroidstudy.ui.home.HomeViewModel
import org.junit.runner.Description

class HomeViewModelTestRule : MainDispatcherRule() {

    lateinit var homeViewModel: HomeViewModel

    override fun starting(description: Description) {
        super.starting(description)
        homeViewModel = HomeViewModel(FakeTodoRepository())
    }
}
