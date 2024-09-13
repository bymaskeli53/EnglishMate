package com.example.englishmate

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import com.example.englishmate.databinding.WordBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson

class WordBottomSheet : BottomSheetDialogFragment() {
    private var _binding: WordBottomSheetBinding? = null

    private val binding get() = _binding!!

    private val navArgs: WordBottomSheetArgs by navArgs()

    private lateinit var preferenceHelper: PreferenceHelper

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
        val word = navArgs.Word
        binding.tvBottomSheet.text = word.turkish

        preferenceHelper = PreferenceHelper(requireContext())

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                preferenceHelper.saveLearnedWord(word.english)
                Toast.makeText(requireContext(),"Word Learned",Toast.LENGTH_SHORT).show()
                setFragmentResult("learned_word",Bundle().apply {
                    putParcelable("word",word)
                })
                preferenceHelper.saveLearnedWord(word.english)
                (parentFragment as? HomeFragment)?.removeWordFromList(word)
                dismiss()
            }
    }
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