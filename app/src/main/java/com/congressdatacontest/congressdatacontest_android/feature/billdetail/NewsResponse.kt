package com.congressdatacontest.congressdatacontest_android.feature.billdetail

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(
    @SerializedName("title") val title: String,
    @SerializedName("link") val link: String,
    @SerializedName("image") val image: String,
    @SerializedName("positive") val positive: Double,
    @SerializedName("neutral") val neutral: Double,
    @SerializedName("negative") val negative: Double,
) : Parcelable

@Parcelize
data class NewsData(
    val title: String,
    val link: String,
    val image: String,
    val positive: Double,
    val neutral: Double,
    val negative: Double,
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
