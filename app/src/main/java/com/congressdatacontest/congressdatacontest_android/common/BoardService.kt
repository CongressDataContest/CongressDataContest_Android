package com.congressdatacontest.congressdatacontest_android.common

import com.congressdatacontest.congressdatacontest_android.feature.board.BillResponseItem
import com.congressdatacontest.congressdatacontest_android.feature.board.SearchRequest
import com.congressdatacontest.congressdatacontest_android.feature.board.TagRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BoardService {
    @POST("api/bill/filtering")
    fun getBoardFiltering(
        @Body search: SearchRequest
    ): Call<List<BillResponseItem>>

    @POST("api/bill/tag")
    fun getBoard(
        @Body board: TagRequest
    ): Call<List<BillResponseItem>>
}
