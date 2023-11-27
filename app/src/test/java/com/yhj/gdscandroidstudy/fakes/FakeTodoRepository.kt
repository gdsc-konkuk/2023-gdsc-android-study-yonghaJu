package com.yhj.gdscandroidstudy.fakes

import com.yhj.gdscandroidstudy.domain.TodoItem
import com.yhj.gdscandroidstudy.domain.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTodoRepository : TodoRepository {
    private val todos = MutableStateFlow(listOf<TodoItem>())
    override fun getTodos(): Flow<List<TodoItem>> {
        return todos
    }

    override suspend fun setTodo(todoItem: TodoItem) {
        todos.emit(todos.value.map { item -> if (item.id == todoItem.id) todoItem else item })
    }

    override suspend fun addTodo(todoItem: TodoItem) {
        todos.emit(todos.value + listOf(todoItem))
    }

    override suspend fun removeTodo(todoItem: TodoItem) {
        todos.emit(todos.value.filter { item -> item.id != todoItem.id })
    }
}
