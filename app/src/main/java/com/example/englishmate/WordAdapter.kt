package com.example.englishmate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.englishmate.databinding.ItemWordsBinding

class WordAdapter(var wordList: MutableList<Word>, val itemClick: (Word) -> Unit = {}) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {


    inner class WordViewHolder(val binding: ItemWordsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
       holder.binding.tvWord.text = wordList[position].english
        holder.binding.root.setOnClickListener {
            itemClick(wordList[position])
        }
    }

    // Yeni kelime listesini ayarlamak için bir fonksiyon ekliyoruz
    fun setWordLists(newWordList: List<Word>) {
        wordList = newWordList.toMutableList()
        notifyDataSetChanged() // Liste güncellendiğini bildir
    }

    fun removeWord(word: Word) {
        val position = wordList.indexOf(word)
        if (position != -1) {
            wordList.removeAt(position) // Kelimeyi listeden kaldır
            notifyDataSetChanged() // RecyclerView'i güncelle
        }
    }

    fun addWord(word: Word) {
        wordList.add(word)
        notifyItemInserted(wordList.size - 1)
    }


}