package com.congressdatacontest.congressdatacontest_android.common

import com.congressdatacontest.congressdatacontest_android.feature.board.VoteRequest
import com.congressdatacontest.congressdatacontest_android.feature.board.VoteResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface VoteBillService {
    @PATCH("api/bill/vote")
    fun voteBill(
        @Body vote: VoteRequest
    ): Call<VoteResponse>
}
