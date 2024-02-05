package com.congressdatacontest.congressdatacontest_android.feature.board

data class TagRequest(
    val tagId: List<Int>
)

data class SearchRequest(
    var tagId: List<Int> = listOf(),
    var status: String = "전체",
    var orderCondition: String = "LATEST",
    var searchKeyword: String = ""
)
