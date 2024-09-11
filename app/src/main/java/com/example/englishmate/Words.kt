package com.example.englishmate

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Word(
    val english: String,
    val turkish: String
) : Parcelable

data class WordList(
    val words: List<Word>
)