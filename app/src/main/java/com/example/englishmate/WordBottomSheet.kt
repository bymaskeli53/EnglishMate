package com.example.englishmate

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.englishmate.databinding.WordBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.navigation.fragment.navArgs

class WordBottomSheet : BottomSheetDialogFragment() {
    private var _binding: WordBottomSheetBinding? = null

    private val binding get() = _binding!!

    private val navArgs: WordBottomSheetArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WordBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvBottomSheet.text = navArgs.Word.turkish
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCanceledOnTouchOutside(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}