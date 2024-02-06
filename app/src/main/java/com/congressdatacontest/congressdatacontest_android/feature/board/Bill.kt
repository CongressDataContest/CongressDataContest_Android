package com.congressdatacontest.congressdatacontest_android.feature.board

import android.os.Parcelable
import com.congressdatacontest.congressdatacontest_android.feature.billdetail.NewsResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bill(
    @SerializedName("bill") val billInfo: BillInfo,
    @SerializedName("newsList") val newsResponseList: List<NewsResponse>,
) : Parcelable

@Parcelize
data class BillInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("proposer") val proposer: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("majorTagName") val majorTagName: String?,
    @SerializedName("minorTagName") val minorTagName: String?,
    @SerializedName("pageContent") val pageContent: String?,
    @SerializedName("registerDate") val registerDate: String?,
    @SerializedName("referedDepartmentDate") val referredDepartmentDate: String?,
    @SerializedName("referedCourtDate") val referredCourtDate: String?,
    @SerializedName("decisionDate") val decisionDate: String?,
    @SerializedName("upVoteCount") val upVoteCount: Int,
    @SerializedName("middleVoteCount") val middleVoteCount: Int,
    @SerializedName("downVoteCount") val downVoteCount: Int,
) : Parcelable

@Parcelize
data class BillInfoData(
    val id: Int,
    val title: String?,
    val proposer: String?,
    val status: String?,
    val majorTagName: String?,
    val minorTagName: String?,
    val pageContent: String?,
    val registerDate: String?,
    val referredDepartmentDate: String?,
    val referredCourtDate: String?,
    val decisionDate: String?,
    val upVoteCount: Int,
    val middleVoteCount: Int,
    val downVoteCount: Int,
) : Parcelable

fun BillInfo.toEntity(): BillInfoData {
    return BillInfoData(
        id = id,
        title = title,
        proposer = proposer,
        status = status,
        majorTagName = majorTagName,
        minorTagName = minorTagName,
        pageContent = pageContent,
        registerDate = registerDate,
        referredDepartmentDate = referredDepartmentDate,
        referredCourtDate = referredCourtDate,
        decisionDate = decisionDate,
        upVoteCount = upVoteCount,
        middleVoteCount = middleVoteCount,
        downVoteCount = downVoteCount
    )
}
