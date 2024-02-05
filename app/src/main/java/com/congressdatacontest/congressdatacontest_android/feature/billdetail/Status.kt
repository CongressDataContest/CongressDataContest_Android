package com.congressdatacontest.congressdatacontest_android.feature.billdetail

enum class Status(val status: String) {
    ONE("접수"),
    TWO("위원회심사"),
    THREE("체계자구심사"),
    FOUR("본회의심의"),
    FIVE("정부이송"),
    SIX("공포")
}
