package com.example.englishmate

data class Word(
    val english: String,
    val turkish: String
)

data class WordList(
    val words: List<Word>
)