package com.yhj.gdscandroidstudy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yhj.gdscandroidstudy.databinding.FragmentHomeBinding
import com.yhj.gdscandroidstudy.util.collectWhenStarted
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todoAdapter = TodoAdapter { item -> viewModel.clickItem(item) }

        binding.lvTodoItem.adapter = todoAdapter

        viewLifecycleOwner.collectWhenStarted(viewModel.todoList) { todoList ->
            todoAdapter.submitList(todoList)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
