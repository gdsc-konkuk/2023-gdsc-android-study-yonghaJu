package com.yhj.gdscandroidstudy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yhj.gdscandroidstudy.domain.TodoItem
import com.yhj.gdscandroidstudy.domain.TodoRepository
import com.yhj.gdscandroidstudy.util.SUBSCRIPTION_TIMEOUT
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val todoRepository: TodoRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            TodoItem.DUMMY.forEach {
                todoRepository.addTodo(it)
            }
        }
    }

    val todoList = todoRepository.getTodos().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(SUBSCRIPTION_TIMEOUT),
        initialValue = listOf(),
    )

    fun clickItem(clickedItem: TodoItem) {
        viewModelScope.launch {
            todoRepository.setTodo(clickedItem.copy(isCompleted = clickedItem.isCompleted.not()))
        }
    }
}
