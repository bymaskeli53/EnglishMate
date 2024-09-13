package com.example.englishmate

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.englishmate.databinding.FragmentHomeBinding
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var preferenceHelper: PreferenceHelper
    private lateinit var wordAdapter: WordAdapter

    // HomeFragmentViewModel'i burada tanımlayın
    private val viewModel: HomeFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        preferenceHelper = PreferenceHelper(requireContext())

        val wordList = loadWordsFromJson().words.toMutableList()
        val initialWordList = wordList.filterNot { preferenceHelper.getLearnedWords().contains(it.english) }

        wordAdapter = WordAdapter {
            val action = HomeFragmentDirections.actionHomeFragmentToWordBottomSheet(it)
            findNavController().navigate(action)
        }
        binding.rvWords.adapter = wordAdapter
        binding.rvWords.layoutManager = GridLayoutManager(requireContext(), 2)

        // Eğer ViewModel'de liste boşsa shuffle yap, değilse var olan listeyi kullan
        if (viewModel.shuffledWordList.isEmpty()) {
            viewModel.shuffledWordList = initialWordList.shuffled()
        }
        wordAdapter.setWordLists(viewModel.shuffledWordList)

        // Swipe-to-refresh ile listeyi yenileme
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.shuffledWordList = wordList.filterNot { preferenceHelper.getLearnedWords().contains(it.english) }.shuffled()
            wordAdapter.submitList(viewModel.shuffledWordList) {
                binding.rvWords.scrollToPosition(0)
            }
            binding.swipeToRefresh.isRefreshing = false
        }

        // Öğrenilen kelimeyi çıkarmak için listener
        setFragmentResultListener("learned_word") { _, bundle ->
            val learnedWord = bundle.getParcelable<Word>("word")
            learnedWord?.let { removeWordFromList(it) }
        }
    }

    private fun removeWordFromList(word: Word) {
        val currentList = viewModel.shuffledWordList.toMutableList()
        currentList.remove(word)
        viewModel.shuffledWordList = currentList
        wordAdapter.setWordLists(currentList)
    }

    private fun loadWordsFromJson(): WordList {
        val inputStream = activity?.assets?.open("words.json")
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val jsonString = bufferedReader.use { it.readText() }

        val gson = Gson()
        return gson.fromJson(jsonString, WordList::class.java)
    }
}


