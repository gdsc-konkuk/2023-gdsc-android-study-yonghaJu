package com.yhj.gdscandroidstudy.data.todo

import com.yhj.gdscandroidstudy.domain.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoDataSource {
    fun getTodos(): Flow<List<TodoItem>>
    suspend fun setTodo(todoItem: TodoItem)

    suspend fun addTodo(todoItem: TodoItem)

    suspend fun removeTodo(todoItem: TodoItem)
}
