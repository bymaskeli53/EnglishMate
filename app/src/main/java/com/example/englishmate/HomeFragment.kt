package com.example.englishmate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.englishmate.databinding.FragmentHomeBinding
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        val wordList = loadWordsFromJson()
        binding.rvWords.adapter = WordAdapter(wordList.words)


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