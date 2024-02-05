package com.congressdatacontest.congressdatacontest_android.common

import com.congressdatacontest.congressdatacontest_android.feature.board.Bill
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetBillDetailService {
    @GET("api/bill/detail")
    fun getBillDetail(
        @Query("id") id: Int
    ): Call<Bill>
}
