package com.example.englishmate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.englishmate.databinding.FragmentHomeBinding
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var preferenceHelper: PreferenceHelper

    private lateinit var wordAdapter: WordAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        preferenceHelper = PreferenceHelper(requireContext())

        val wordList = loadWordsFromJson().words.toMutableList()
        wordAdapter = WordAdapter(wordList.filterNot { preferenceHelper.getLearnedWords().contains(it.english) }.shuffled().toMutableList(),{
            val action = HomeFragmentDirections.actionHomeFragmentToWordBottomSheet(it)
            findNavController().navigate(action)
        })
        binding.rvWords.adapter = wordAdapter

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvWords.layoutManager = layoutManager


        binding.swipeToRefresh.setOnRefreshListener {
            val newList = wordList.shuffled()
            wordAdapter.setWordLists(newList)
            binding.swipeToRefresh.isRefreshing = false
        }

        setFragmentResultListener("learned_word"){_,bundle ->
            val learnedWord = bundle.getParcelable<Word>("word")
            if (learnedWord != null) {
                removeWordFromList(learnedWord)
            }
        }
    }

    private fun loadWordsFromJson(): WordList {
        val inputStream = activity?.assets?.open("words.json")
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val jsonString = bufferedReader.use { it.readText() }

        // Gson ile JSON'u nesnelere dönüştür
        val gson = Gson()
        return gson.fromJson(jsonString, WordList::class.java)
    }

    fun removeWordFromList(word: Word) {
        wordAdapter.removeWord(word)
    }

    fun addWordToList(word: Word) {
        wordAdapter.addWord(word)
    }


}
