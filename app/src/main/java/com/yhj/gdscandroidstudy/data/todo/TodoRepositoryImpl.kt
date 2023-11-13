package com.yhj.gdscandroidstudy.data.todo

import com.yhj.gdscandroidstudy.domain.TodoItem
import com.yhj.gdscandroidstudy.domain.TodoRepository
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val todoLocalDataSource: TodoDataSource) : TodoRepository {
    override fun getTodos(): Flow<List<TodoItem>> {
        return todoLocalDataSource.getTodos()
    }

    override suspend fun setTodo(todoItem: TodoItem) {
        todoLocalDataSource.setTodo(todoItem)
    }

    override suspend fun addTodo(todoItem: TodoItem) {
        todoLocalDataSource.addTodo(todoItem)
    }

    override suspend fun removeTodo(todoItem: TodoItem) {
        todoLocalDataSource.removeTodo(todoItem)
    }
}
