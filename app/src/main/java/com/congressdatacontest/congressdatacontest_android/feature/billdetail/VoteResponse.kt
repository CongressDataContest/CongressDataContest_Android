package com.congressdatacontest.congressdatacontest_android.feature.billdetail

import com.google.gson.annotations.SerializedName

data class VoteResponse(
    @SerializedName("vote") val vote: String,
    @SerializedName("value") val value: Int
)

data class VoteData(
    val vote: String,
    val value: Int
)

fun VoteResponse.toEntity(): VoteData {
    return VoteData(
        vote = vote,
        value = value
    )
}
