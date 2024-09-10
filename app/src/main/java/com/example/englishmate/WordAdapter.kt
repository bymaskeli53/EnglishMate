package com.example.englishmate

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.englishmate.databinding.ItemWordsBinding

class WordAdapter(val wordList: List<Word>) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {


    inner class WordViewHolder(private val binding: ItemWordsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}