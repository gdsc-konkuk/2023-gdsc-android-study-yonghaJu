package com.yhj.gdscandroidstudy.ui.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yhj.gdscandroidstudy.databinding.ActivityEditBinding
import com.yhj.gdscandroidstudy.util.collectWhenStarted
import com.yhj.gdscandroidstudy.util.showSnackBar
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
                binding.root.showSnackBar(event.message)
            }

            is Event.SavingSuccess -> {
                finish()
            }

            Event.BackButtonClicked -> {
                finish()
            }
        }
    }
}
