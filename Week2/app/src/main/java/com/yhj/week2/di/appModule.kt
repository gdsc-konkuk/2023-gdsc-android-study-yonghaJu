package com.yhj.week2.di // ktlint-disable filename

import com.yhj.week2.data.UserRepository
import com.yhj.week2.ui.edit.EditViewModel
import com.yhj.week2.ui.home.HomeViewModel
import com.yhj.week2.ui.mypage.MyPageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { UserRepository(get()) }
    viewModel { EditViewModel(get()) }
    viewModel { MyPageViewModel(get()) }
    viewModel { HomeViewModel() }
}
