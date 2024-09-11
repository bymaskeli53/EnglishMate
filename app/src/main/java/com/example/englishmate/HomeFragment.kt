package com.example.englishmate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.englishmate.databinding.FragmentHomeBinding
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)


        val wordList = loadWordsFromJson()
        val wordAdapter = WordAdapter(wordList.words,{
            val action = HomeFragmentDirections.actionHomeFragmentToWordBottomSheet(it)
            findNavController().navigate(action)
        })
        binding.rvWords.adapter = wordAdapter


        binding.swipeToRefresh.setOnRefreshListener {
            val newList = wordList.words.shuffled()
            wordAdapter.setWordLists(newList)
            binding.swipeToRefresh.isRefreshing = false
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
}
