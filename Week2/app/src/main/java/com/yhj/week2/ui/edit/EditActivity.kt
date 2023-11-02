package com.yhj.week2.ui.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.yhj.week2.databinding.ActivityEditBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private val viewModel: EditViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        setContentView(binding.root)

        collectEvent()
    }

    private fun collectEvent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect { event ->
                    handleEvent(event)
                }
            }
        }
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.SavingFailed -> {
                Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
            }

            is Event.SavingSuccess -> {
                Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
            }

            Event.BackButtonClicked -> {
                finish()
            }
        }
    }
}
