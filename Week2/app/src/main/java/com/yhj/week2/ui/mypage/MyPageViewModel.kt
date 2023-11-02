package com.yhj.week2.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yhj.week2.data.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MyPageViewModel(userRepository: UserRepository) : ViewModel() {

    val name = userRepository.userNameFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        "",
    )

    val eventFlow = MutableSharedFlow<Event>()

    fun onClickEdit() {
        viewModelScope.launch {
            eventFlow.emit(Event.EditClicked)
        }
    }
}

sealed class Event {
    data object EditClicked : Event()
}
