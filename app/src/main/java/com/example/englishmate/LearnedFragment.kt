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

        // Öğrenilen kelimeleri preference'dan alıyoruz
        val learnedWords = preferenceHelper.getLearnedWords().map { Word(it, it) }

        // WordAdapter'ı ayarlıyoruz
        wordAdapter = WordAdapter { word ->
            // Unlearn işlemi için BottomSheet'i açıyoruz
            val action = LearnedFragmentDirections.actionLearnedFragmentToLearnedBottomSheet(word)
            findNavController().navigate(action)
        }

        binding.rvWords.adapter = wordAdapter

        // İlk listeyi adapter'a ekliyoruz
        wordAdapter.setWordLists(learnedWords)

        // Unlearned işlemi yapıldığında kelimeyi listeden çıkarıyoruz
        setFragmentResultListener("unlearned_word") { _, bundle ->
            val unlearnedWord = bundle.getParcelable<Word>("word2")
            unlearnedWord?.let { removeWordFromList(it) }
        }
    }

    // Kelimeyi listeden çıkaran fonksiyon
    fun removeWordFromList(word: Word) {
        val currentList = wordAdapter.currentList.toMutableList()
        currentList.remove(word)
        wordAdapter.setWordLists(currentList)
    }
}

