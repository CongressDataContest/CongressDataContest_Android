package com.congressdatacontest.congressdatacontest_android.feature.billdetail

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(
    @SerializedName("title") val title: String,
    @SerializedName("link") val link: String,
    @SerializedName("image") val image: String,
    @SerializedName("positive") val positive: Int,
    @SerializedName("neutral") val neutral: Int,
    @SerializedName("negative") val negative: Int,
) : Parcelable

@Parcelize
data class NewsData(
    val title: String,
    val link: String,
    val image: String,
    val positive: Int,
    val neutral: Int,
    val negative: Int,
) : Parcelable

fun NewsResponse.toEntity(): NewsData {
    return NewsData(
        title = title,
        link = link,
        image = image,
        positive = positive,
        neutral = neutral,
        negative = negative
    )
}
