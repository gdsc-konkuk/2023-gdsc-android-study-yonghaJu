package com.yhj.gdscandroidstudy.data.todo

import com.yhj.gdscandroidstudy.domain.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TodoLocalDataSource(private val dao: TodoDao) : TodoDataSource {
    override fun getTodos(): Flow<List<TodoItem>> {
        return dao.getAll().map { todoEntityList ->
            todoEntityList.map { todoEntity ->
                Mapper.toTodoItem(todoEntity)
            }
        }
    }

    override suspend fun setTodo(todoItem: TodoItem) = withContext(Dispatchers.IO) {
        dao.insert(Mapper.toTodoEntity(todoItem))
    }

    override suspend fun addTodo(todoItem: TodoItem) = withContext(Dispatchers.IO) {
        dao.insert(Mapper.toTodoEntity(todoItem))
    }

    override suspend fun removeTodo(todoItem: TodoItem) = withContext(Dispatchers.IO) {
        dao.delete(Mapper.toTodoEntity(todoItem))
    }
}
