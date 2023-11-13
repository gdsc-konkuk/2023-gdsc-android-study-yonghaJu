package com.yhj.gdscandroidstudy.domain

import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<TodoItem>>
    suspend fun setTodo(todoItem: TodoItem)

    suspend fun addTodo(todoItem: TodoItem)

    suspend fun removeTodo(todoItem: TodoItem)
}
