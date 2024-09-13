package com.example.englishmate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.englishmate.databinding.ItemWordsBinding

class WordAdapter(val itemClick: (Word) -> Unit = {}) :
    ListAdapter<Word, WordAdapter.WordViewHolder>(WordDiffCallback()) {

    inner class WordViewHolder(val binding: ItemWordsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = getItem(position)
        holder.binding.tvWord.text = word.english
        holder.binding.root.setOnClickListener {
            itemClick(word)
        }
    }

    // Yeni kelime listesini ayarlamak için bir fonksiyon ekliyoruz
    fun setWordLists(newWordList: List<Word>) {
        submitList(newWordList)


        }
    }


class WordDiffCallback : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.english == newItem.english
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }
}
