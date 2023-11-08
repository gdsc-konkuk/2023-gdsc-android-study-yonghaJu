package com.yhj.gdscandroidstudy.ui.home

import androidx.lifecycle.ViewModel
import com.yhj.gdscandroidstudy.domain.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel : ViewModel() {
    val todoList = MutableStateFlow(TodoItem.DUMMY)

    fun clickItem(clickedItem: TodoItem) {
        val newValue = clickedItem.isCompleted.not()
        todoList.value = todoList.value.map {
            it.copy(
                id = it.id,
                title = it.title,
                isCompleted = if (clickedItem.id == it.id) newValue else it.isCompleted,
            )
        }
    }
}
