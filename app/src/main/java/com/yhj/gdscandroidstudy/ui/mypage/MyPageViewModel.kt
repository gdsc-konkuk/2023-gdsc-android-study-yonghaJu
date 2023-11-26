package com.yhj.gdscandroidstudy.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yhj.gdscandroidstudy.domain.TodoRepository
import com.yhj.gdscandroidstudy.domain.UserRepository
import com.yhj.gdscandroidstudy.util.SUBSCRIPTION_TIMEOUT
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MyPageViewModel(
    userRepository: UserRepository,
    private val todoRepository: TodoRepository,
) : ViewModel() {

    val name = userRepository.userNameFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(SUBSCRIPTION_TIMEOUT),
        initialValue = "",
    )

    val completedTodoNum =
        todoRepository.getTodos().map { todoList -> todoList.count { it.isCompleted } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(SUBSCRIPTION_TIMEOUT),
                initialValue = 0,
            ).asLiveData()

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
