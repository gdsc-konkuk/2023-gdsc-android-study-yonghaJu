package com.yhj.gdscandroidstudy.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yhj.gdscandroidstudy.collectWhenStarted
import com.yhj.gdscandroidstudy.databinding.FragmentMypageBinding
import com.yhj.gdscandroidstudy.ui.edit.EditActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageFragment : Fragment() {
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: MyPageViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMypageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewLifecycleOwner.collectWhenStarted(viewModel.eventFlow) {
            startActivity(Intent(requireContext(), EditActivity::class.java))
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
