package com.example.englishmate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.englishmate.databinding.FragmentLearnedBinding

class LearnedFragment : Fragment(R.layout.fragment_learned) {
    private lateinit var wordAdapter: WordAdapter
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLearnedBinding.bind(view)
        preferenceHelper = PreferenceHelper(requireContext())

        val learnedWords = preferenceHelper.getLearnedWords().map { Word(it, "Some Translation") }.toMutableList()
        wordAdapter = WordAdapter(learnedWords) { word ->
            // Unlearn işlemi
            val action = LearnedFragmentDirections.actionLearnedFragmentToLearnedBottomSheet(word)
            findNavController().navigate(action)
           // removeWordFromList(word)
            // Ana Fragment'a geri ekleme işlemi yapılabilir
           // (parentFragmentManager.findFragmentById(R.id.homeFragment) as? HomeFragment)?.addWordToList(word)
        }
        binding.rvWords.adapter = wordAdapter

        setFragmentResultListener("unlearned_word"){_,bundle ->
            val unlearnedWord = bundle.getParcelable<Word>("word2")
            if (unlearnedWord != null) {
                removeWordFromList(unlearnedWord)
            }
        }
    }

    fun removeWordFromList(word: Word) {
        wordAdapter.removeWord(word)
    }
}
