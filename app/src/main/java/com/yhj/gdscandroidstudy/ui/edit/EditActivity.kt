package com.yhj.gdscandroidstudy.ui.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.yhj.gdscandroidstudy.collectWhenStarted
import com.yhj.gdscandroidstudy.databinding.ActivityEditBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private val viewModel: EditViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        collectEvent()
    }

    private fun collectEvent() {
        collectWhenStarted(viewModel.eventFlow) { event ->
            handleEvent(event)
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
