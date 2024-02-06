package com.congressdatacontest.congressdatacontest_android.feature.board

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BillResponseItem(
    val id: Int,
    val title: String?,
    val billNumber: Int,
    val proposer: String?,
    val registerDate: String?,
    val majorTagName: String?,
    val minorTagName: String?,
    val status: String?
): Parcelable
