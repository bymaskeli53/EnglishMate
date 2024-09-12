package com.example.englishmate

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import com.example.englishmate.databinding.ItemWordsBinding

class LearnedAdapter(var wordList: List<Word>, val itemClick: (Word) -> Unit = {}) : RecyclerView.Adapter<LearnedAdapter.LearnViewHolder>() {


    inner class LearnViewHolder(val binding: ItemWordsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnViewHolder {
        val binding = ItemWordsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LearnViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: LearnViewHolder, position: Int) {
        holder.binding.tvWord.text = wordList[position].english
        holder.binding.root.setOnClickListener {
            itemClick(wordList[position])
        }
    }

    // Yeni kelime listesini ayarlamak için bir fonksiyon ekliyoruz
    fun setWordLists(newWordList: List<Word>) {
        wordList = newWordList
        notifyDataSetChanged() // Liste güncellendiğini bildir
    }


}