package com.example.englishmate

import android.content.Context

class PreferenceHelper(
    context: Context,
) {
    private val preferences = context.getSharedPreferences("learned_words", Context.MODE_PRIVATE)

    fun saveLearnedWord(word: String) {
        val learnedWords = getLearnedWords().toMutableSet()
        learnedWords.add(word)
        preferences.edit().putStringSet("learned_words", learnedWords).apply()
    }

    fun removeLearnedWord(word: String) {
        val learnedWords = getLearnedWords().toMutableSet()
        learnedWords.remove(word)
        preferences.edit().putStringSet("learned_words", learnedWords).apply()
    }

    fun getLearnedWords(): Set<String> = preferences.getStringSet("learned_words", emptySet()) ?: emptySet()
}
