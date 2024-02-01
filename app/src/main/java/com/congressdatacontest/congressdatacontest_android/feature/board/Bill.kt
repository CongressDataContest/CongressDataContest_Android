package com.congressdatacontest.congressdatacontest_android.feature.board

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bill(
    val id: Int,
    val status: String,
    val title: String,
    val suggester: String,
    val date: String,
    val content: String,
    val majorTagName: String,
    val minorTagName: String,
    val likes: Int,
    val noResponses: Int,
    val dislikes: Int,
) : Parcelable
