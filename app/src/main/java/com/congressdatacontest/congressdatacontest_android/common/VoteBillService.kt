package com.congressdatacontest.congressdatacontest_android.common

import com.congressdatacontest.congressdatacontest_android.feature.billdetail.VoteRequest
import com.congressdatacontest.congressdatacontest_android.feature.billdetail.VoteResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface VoteBillService {
    @PATCH("api/bill/vote")
    fun voteBill(
        @Body vote: VoteRequest
    ): Call<VoteResponse>
}
