package com.yhj.gdscandroidstudy.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yhj.gdscandroidstudy.databinding.ItemTodoBinding
import com.yhj.gdscandroidstudy.domain.TodoItem

class TodoAdapter(private val onCheckTodoItem: (TodoItem) -> Unit) :
    ListAdapter<TodoItem, TodoAdapter.ViewHolder>(diffUtil) {
    class ViewHolder(
        private val binding: ItemTodoBinding,
        private val onCheckTodoItem: (TodoItem) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoItem: TodoItem) {
            binding.item = todoItem
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onCheckTodoItem(todoItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onCheckTodoItem,
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TodoItem>() {
            override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
