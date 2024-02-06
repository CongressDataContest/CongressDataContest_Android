package com.congressdatacontest.congressdatacontest_android.feature.billdetail

import android.content.Intent
import android.graphics.Rect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.congressdatacontest.congressdatacontest_android.R
import com.congressdatacontest.congressdatacontest_android.common.GetBillDetailService
import com.congressdatacontest.congressdatacontest_android.common.RetrofitClient
import com.congressdatacontest.congressdatacontest_android.common.VoteBillService
import com.congressdatacontest.congressdatacontest_android.databinding.ActivityBillDetailBinding
import com.congressdatacontest.congressdatacontest_android.feature.board.Bill
import com.congressdatacontest.congressdatacontest_android.feature.board.BillResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBillDetailBinding
    private lateinit var newsAdapter: NewsAdapter
    private var currentUpValue: Int = 0
    private var currentMiddleValue: Int = 0
    private var currentDownValue: Int = 0
    private var upClicked: Boolean = false
    private var middleClicked: Boolean = false
    private var downClicked: Boolean = false

    private var getBillDetailService: GetBillDetailService = RetrofitClient.provideRetrofit().create(GetBillDetailService::class.java)
    private var voteBillService: VoteBillService = RetrofitClient.provideRetrofit().create(VoteBillService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBillDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListeners()
    }

    private fun getBillData(): BillResponseItem? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("bill", BillResponseItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("bill")
        }
    }

    private fun initView() {
        initRecyclerView(binding.rvNewsList)

        Log.i("initView", "BillData: ${getBillData()}, " +
                "billId: ${getBillData()?.id}")

        val call = getBillDetailService.getBillDetail(getBillData()?.id!!)

        Log.i("initView", "call: $call")

        call.enqueue(object : Callback<Bill> {
            override fun onResponse(call: Call<Bill>, response: Response<Bill>) {
                if (response.code() == 200) {
                    Log.i("onResponse", "response: ${response.body()}")
                    binding.tvBillTitle.text = response.body()?.billInfo?.title
                    binding.tvBillSuggesterValue.text = if (response.body()?.billInfo?.proposer != null) {
                        response.body()?.billInfo?.proposer
                    } else {
                        "위원장"
                    }
                    binding.tvBillSuggesterDateValue.text = response.body()?.billInfo?.registerDate
                    binding.tvBillStatusValue.text = response.body()?.billInfo?.status
                    binding.tvBillDetailContentValue.text = response.body()?.billInfo?.pageContent?.substring(response.body()?.billInfo?.title?.length!! + 12)
                    binding.tvBillParentCategory.text = response.body()?.billInfo?.majorTagName
                    binding.tvBillSubCategory.text = response.body()?.billInfo?.minorTagName

                    binding.billEvaluationProcess01.tvDate01.text = response.body()?.billInfo?.registerDate
                    binding.billEvaluationProcess02.tvDate02.text = response.body()?.billInfo?.referredDepartmentDate
                    binding.billEvaluationProcess03.tvDate03.text = response.body()?.billInfo?.referredCourtDate
                    binding.billEvaluationProcess04.tvDate04.text = response.body()?.billInfo?.decisionDate
                    binding.billEvaluationProcess05.tvDate05.text = ""
                    binding.billEvaluationProcess06.tvDate06.text = ""

                    binding.tvLikes.text = response.body()?.billInfo?.upVoteCount.toString()
                    binding.tvNotKnow.text = response.body()?.billInfo?.middleVoteCount.toString()
                    binding.tvDislikes.text = response.body()?.billInfo?.downVoteCount.toString()

                    Log.i("onResponse", "newsResponseList: ${response.body()?.newsResponseList?.map {
                        it.toEntity()
                    }}")
                    newsAdapter.submitList(response.body()?.newsResponseList?.map {
                        it.toEntity()
                    })

                    // 뉴스가 0개일 때
                    if (response.body()?.newsResponseList?.map {
                            it.toEntity()
                        }?.isEmpty() == true) {
                        binding.layoutRelatedNews.isGone = true
                        binding.layoutRelatedNews.isVisible = false
                    } else { // 뉴스가 1 ~ 3개일 때
                        binding.layoutRelatedNews.isGone = false
                        binding.layoutRelatedNews.isVisible = true
                    }

                    Log.i("initView", "NewsList: ${newsAdapter.currentList}," +
                            "isGone = ${binding.rvNewsList.isGone}, " +
                            "isVisible = ${binding.rvNewsList.isVisible}")

                    when (response.body()?.billInfo?.status) {
                        Status.ONE.status -> {
                            binding.billEvaluationProcess01.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess02.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.white))
                            binding.billEvaluationProcess03.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.white))
                            binding.billEvaluationProcess04.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.white))
                            binding.billEvaluationProcess05.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.white))
                            binding.billEvaluationProcess06.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.white))

                            binding.ivProcess01.setImageResource(R.drawable.ic_right_arrow_double)
                            binding.ivProcess02.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess03.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess04.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess05.setImageResource(R.drawable.ic_right_arrow)
                        }
                        Status.TWO.status -> {
                            binding.billEvaluationProcess01.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess02.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess03.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.white))
                            binding.billEvaluationProcess04.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.white))
                            binding.billEvaluationProcess05.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.white))
                            binding.billEvaluationProcess06.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.white))

                            binding.ivProcess01.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess02.setImageResource(R.drawable.ic_right_arrow_double)
                            binding.ivProcess03.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess04.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess05.setImageResource(R.drawable.ic_right_arrow)
                        }
                        Status.THREE.status -> {
                            binding.billEvaluationProcess01.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess02.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess03.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess04.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.white))
                            binding.billEvaluationProcess05.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.white))
                            binding.billEvaluationProcess06.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.white))

                            binding.ivProcess01.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess02.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess03.setImageResource(R.drawable.ic_right_arrow_double)
                            binding.ivProcess04.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess05.setImageResource(R.drawable.ic_right_arrow)
                        }
                        Status.FOUR.status -> {
                            binding.billEvaluationProcess01.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess02.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess03.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess04.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess05.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.white))
                            binding.billEvaluationProcess06.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.white))

                            binding.ivProcess01.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess02.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess03.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess04.setImageResource(R.drawable.ic_right_arrow_double)
                            binding.ivProcess05.setImageResource(R.drawable.ic_right_arrow)
                        }
                        Status.FIVE.status -> {
                            binding.billEvaluationProcess01.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess02.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess03.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess04.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess05.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess06.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.white))

                            binding.ivProcess01.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess02.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess03.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess04.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess05.setImageResource(R.drawable.ic_right_arrow_double)
                        }
                        Status.SIX.status -> {
                            binding.billEvaluationProcess01.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess02.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess03.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess04.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess05.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess06.cvInnerProcess.setCardBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))

                            binding.ivProcess01.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess02.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess03.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess04.setImageResource(R.drawable.ic_right_arrow)
                            binding.ivProcess05.setImageResource(R.drawable.ic_right_arrow)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Bill>, t: Throwable) {
                Log.e("onFailure1", "error: ${t.message}")
                t.stackTrace
            }
        })
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        newsAdapter = NewsAdapter(
            onClick = ::onClickNewsItem
        )

        val spaceDecoration = VerticalSpaceItemDecoration(20)
        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, LinearLayoutManager(this).orientation)
        recyclerView.run {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(spaceDecoration)
            addItemDecoration(dividerItemDecoration)
        }
    }

    inner class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = verticalSpaceHeight
        }
    }

    private fun onClickNewsItem(newsData: NewsData) {
        val intent = Intent(this, NewsWebViewActivity::class.java)
            .putExtra("link", newsData.link)
        Log.i("onClickNewsItem", "link: ${newsData.link}")
        startActivity(intent)
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.layoutLike.setOnClickListener {
            /*
            - 클릭할 때
              1. 이전에 몰라요를 클릭한 상태이면
                1) 현재 좋아요 값이 0이면
                    => myCount, currentUpValue = 1, currentMiddleValue = 0, upClicked = true, middleClicked = false, downClicked = false

              2. 이전에 싫어요를 클릭한 상태이면
                1) 현재 좋아요 값이 0이면
                    => myCount, currentUpValue = 1, currentDownValue = 0, upClicked = true, middleClicked = false, downClicked = false

              3. 이전에 몰라요 또는 싫어요를 클릭하지 않은 상태이면
                1) 현재 좋아요 값이 0이면
                    => myCount, currentUpValue = 1, upClicked = true, middleClicked = false, downClicked = false
                2) 현재 좋아요 값이 1이면
                    => myCount, currentUpValue = 0, upClicked = true, middleClicked = false, downClicked = false
           */

            var myCount = 0
            if (middleClicked) {
                if (currentUpValue == 0) {
                    myCount = 1
                    middleClicked = false
//                    currentUpValue = 1
                }
            } else if (downClicked) {
                if (currentUpValue == 0) {
                    myCount = 1
                    downClicked = false
//                    currentUpValue = 1
                }
            } else if (!middleClicked) {
                if (currentUpValue == 0) {
                    myCount = 1
//                    currentUpValue = 1
                } else {
                    myCount = -1
//                    currentUpValue = 0
                }
            } else if (!downClicked) {
                if (currentUpValue == 0) {
                    myCount = 1
//                    currentUpValue = 1
                } else {
                    myCount = -1
//                    currentUpValue = 0
                }
            }

            Log.i("layoutLike", "upClicked: $upClicked, " +
                    "middleClicked: $middleClicked, " +
                    "downClicked: $downClicked, " +
                    "myCount: $myCount")

            val upVoteCall = voteBillService.voteBill(
                VoteRequest(
                    getBillData()?.id!!,
                    Vote.UP.vote,
                    myCount
                )
            )

            val middleVoteCall = voteBillService.voteBill(
                VoteRequest(
                    getBillData()?.id!!,
                    Vote.MIDDLE.vote,
                    -1
                )
            )

            val downVoteCall = voteBillService.voteBill(
                VoteRequest(
                    getBillData()?.id!!,
                    Vote.DOWN.vote,
                    -1
                )
            )

            upVoteCall.enqueue(object : Callback<VoteResponse> {
                override fun onResponse(
                    call: Call<VoteResponse>,
                    response: Response<VoteResponse>,
                ) {
                    if (response.code() == 200) {
                        if (response.body()?.value == 1) {
                            binding.tvLikes.text = response.body()?.value.toString()
                            upClicked = true

                            currentUpValue = 1

                            Log.i("onResponse", "currentUpValue: $currentUpValue upClicked: $upClicked middleClicked: $middleClicked downClicked: $downClicked")

                            if (currentMiddleValue == 1) {
                                middleVoteCall.enqueue(object : Callback<VoteResponse> {
                                    override fun onResponse(
                                        call: Call<VoteResponse>,
                                        response: Response<VoteResponse>,
                                    ) {
                                        if (response.code() == 200) {
                                            binding.tvNotKnow.text = response.body()?.value.toString()
                                            middleClicked = false
                                            currentMiddleValue = 0
                                        }
                                    }

                                    override fun onFailure(call: Call<VoteResponse>, t: Throwable) {
                                        Log.e("onFailure3", "error: ${t.message}")
                                        t.stackTrace
                                        upClicked = false
                                    }

                                })
                            } else if (currentDownValue == 1) {
                                downVoteCall.enqueue(object : Callback<VoteResponse> {
                                    override fun onResponse(
                                        call: Call<VoteResponse>,
                                        response: Response<VoteResponse>,
                                    ) {
                                        if (response.code() == 200) {
                                            binding.tvDislikes.text = response.body()?.value.toString()
                                            middleClicked = false
                                            currentDownValue = 0
                                        }
                                    }

                                    override fun onFailure(call: Call<VoteResponse>, t: Throwable) {
                                        Log.e("onFailure3", "error: ${t.message}")
                                        t.stackTrace
                                        upClicked = false
                                    }

                                })
                            }
                        } else {
                            binding.tvLikes.text = response.body()?.value.toString()
                            upClicked = true

                            currentUpValue = 0
                        }
                    }
                }

                override fun onFailure(call: Call<VoteResponse>, t: Throwable) {
                    Log.e("onFailure3", "error: ${t.message}")
                    t.stackTrace
                    upClicked = false
                }
            })
        }

        binding.layoutNoResponse.setOnClickListener {
            /*
            - 클릭할 때
              1. 이전에 좋아요를 클릭한 상태이면
                1) 현재 몰라요 값이 0이면
                    => myCount, currentMiddleValue = 1, currentUpValue = 0, upClicked = false, middleClicked = true, downClicked = false

              2. 이전에 싫어요를 클릭한 상태이면
                1) 현재 몰라요 값이 0이면
                    => myCount, currentMiddleValue = 1, currentDownValue = 0, upClicked = false, middleClicked = true, downClicked = false

              3. 이전에 좋아요 또는 싫어요를 클릭하지 않은 상태이면
                1) 현재 몰라요 값이 0이면
                    => myCount, currentMiddleValue = 1, upClicked = false, middleClicked = true, downClicked = false
                2) 현재 몰라요 값이 1이면
                    => myCount, currentMiddleValue = 0, upClicked = false, middleClicked = true, downClicked = false
           */

            var myCount = 0
            if (upClicked) {
                if (currentMiddleValue == 0) {
                    myCount = 1
                    upClicked = false
//                    currentMiddleValue = 1
                }
            } else if (downClicked) {
                if (currentMiddleValue == 0) {
                    myCount = 1
                    downClicked = false
//                    currentMiddleValue = 1
                }
            } else if (!upClicked) {
                if (currentMiddleValue == 0) {
                    myCount = 1
//                    currentMiddleValue = 1
                } else {
                    myCount = -1
//                    currentMiddleValue = 0
                }
            } else if (!downClicked) {
                if (currentMiddleValue == 0) {
                    myCount = 1
//                    currentMiddleValue = 1
                } else {
                    myCount = -1
//                    currentMiddleValue = 0
                }
            }

            Log.i("layoutNoResponse", "middleClicked: $middleClicked, " +
                    "upClicked: $upClicked, " +
                    "downClicked: $downClicked, " +
                    "myCount: $myCount")

            val middleVoteCall = voteBillService.voteBill(
                VoteRequest(
                    getBillData()?.id!!,
                    Vote.MIDDLE.vote,
                    myCount
                )
            )

            val upVoteCall = voteBillService.voteBill(
                VoteRequest(
                    getBillData()?.id!!,
                    Vote.UP.vote,
                    -1
                )
            )

            val downVoteCall = voteBillService.voteBill(
                VoteRequest(
                    getBillData()?.id!!,
                    Vote.DOWN.vote,
                    -1
                )
            )

            middleVoteCall.enqueue(object : Callback<VoteResponse> {
                override fun onResponse(
                    call: Call<VoteResponse>,
                    response: Response<VoteResponse>,
                ) {
                    if (response.code() == 200) {
                        if (response.body()?.value == 1) {
                            binding.tvNotKnow.text = response.body()?.value.toString()
                            upClicked = false
                            middleClicked = true
                            downClicked = false

                            currentMiddleValue = 1

                            Log.i("onResponse", "currentMiddleValue: $currentMiddleValue upClicked: $upClicked middleClicked: $middleClicked downClicked: $downClicked")

                            if (currentUpValue == 1) {
                                upVoteCall.enqueue(object : Callback<VoteResponse> {
                                    override fun onResponse(
                                        call: Call<VoteResponse>,
                                        response: Response<VoteResponse>,
                                    ) {
                                        if (response.code() == 200) {
                                            binding.tvLikes.text = response.body()?.value.toString()
                                            upClicked = false
                                            currentUpValue = 0
                                        }
                                    }

                                    override fun onFailure(call: Call<VoteResponse>, t: Throwable) {
                                        Log.e("onFailure3", "error: ${t.message}")
                                        t.stackTrace
                                        upClicked = false
                                    }

                                })
                            } else if (currentDownValue == 1) {
                                downVoteCall.enqueue(object : Callback<VoteResponse> {
                                    override fun onResponse(
                                        call: Call<VoteResponse>,
                                        response: Response<VoteResponse>,
                                    ) {
                                        if (response.code() == 200) {
                                            binding.tvDislikes.text = response.body()?.value.toString()
                                            downClicked = false
                                            currentDownValue = 0
                                        }
                                    }

                                    override fun onFailure(call: Call<VoteResponse>, t: Throwable) {
                                        Log.e("onFailure3", "error: ${t.message}")
                                        t.stackTrace
                                        downClicked = false
                                    }
                                })
                            }
                        } else {
                            binding.tvNotKnow.text = response.body()?.value.toString()
                            upClicked = false
                            middleClicked = true
                            downClicked = false

                            currentMiddleValue = 0

                            Log.i("onResponse", "currentMiddleValue: $currentMiddleValue upClicked: $upClicked middleClicked: $middleClicked downClicked: $downClicked")
                        }
                    }
                }

                override fun onFailure(call: Call<VoteResponse>, t: Throwable) {
                    Log.e("onFailure2", "error: ${t.message}")
                    t.stackTrace
                    middleClicked = false
                }
            })
        }

        binding.layoutDislike.setOnClickListener {
            /*
            - 클릭할 때
              1. 이전에 좋아요를 클릭한 상태이면
                1) 현재 싫어요 값이 0이면
                    => myCount, currentDownValue = 1, currentUpValue = 0, upClicked = false, middleClicked = false, downClicked = true

              2. 이전에 몰라요를 클릭한 상태이면
                1) 현재 싫어요 값이 0이면
                    => myCount, currentDownValue = 1, currentMiddleValue = 0, upClicked = false, middleClicked = false, downClicked = true

              3. 이전에 좋아요 또는 몰라요를 클릭하지 않은 상태이면
                1) 현재 싫어요 값이 0이면
                    => myCount, currentDownValue = 1, upClicked = false, middleClicked = false, downClicked = true
                2) 현재 싫어요 값이 1이면
                    => myCount, currentDownValue = 0, upClicked = false, middleClicked = false, downClicked = true
           */

            var myCount = 0
            if (upClicked) {
                if (currentDownValue == 0) {
                    myCount = 1
                    upClicked = false
//                    currentDownValue = 1
                }
            } else if (middleClicked) {
                if (currentDownValue == 0) {
                    myCount = 1
                    middleClicked = false
//                    currentDownValue = 1
                }
            } else if (!upClicked) {
                if (currentDownValue == 0) {
                    myCount = 1
//                    currentDownValue = 1
                } else {
                    myCount = -1
//                    currentDownValue = 0
                }
            } else if (!middleClicked) {
                if (currentDownValue == 0) {
                    myCount = 1
//                    currentDownValue = 1
                } else {
                    myCount = -1
//                    currentDownValue = 0
                }
            }

            Log.i("layoutDislike", "downClicked: $downClicked, " +
                    "upClicked: $upClicked, " +
                    "middleClicked: $middleClicked, " +
                    "myCount: $myCount")

            val downVoteCall = voteBillService.voteBill(
                VoteRequest(
                    getBillData()?.id!!,
                    Vote.DOWN.vote,
                    myCount
                )
            )

            val upVoteCall = voteBillService.voteBill(
                VoteRequest(
                    getBillData()?.id!!,
                    Vote.UP.vote,
                    -1
                )
            )

            val middleVoteCall = voteBillService.voteBill(
                VoteRequest(
                    getBillData()?.id!!,
                    Vote.MIDDLE.vote,
                    -1
                )
            )

            downVoteCall.enqueue(object : Callback<VoteResponse> {
                override fun onResponse(
                    call: Call<VoteResponse>,
                    response: Response<VoteResponse>,
                ) {
                    if (response.code() == 200) {
                        if (response.body()?.value == 1) {
                            binding.tvDislikes.text = response.body()?.value.toString()
                            downClicked = true

                            currentDownValue = 1

                            Log.i("onResponse", "currentDownValue: $currentDownValue upClicked: $upClicked middleClicked: $middleClicked downClicked: $downClicked")

                            if (currentUpValue == 1) {
                                upVoteCall.enqueue(object : Callback<VoteResponse> {
                                    override fun onResponse(
                                        call: Call<VoteResponse>,
                                        response: Response<VoteResponse>,
                                    ) {
                                        if (response.code() == 200) {
                                            binding.tvLikes.text = response.body()?.value.toString()
                                            upClicked = false

                                            currentUpValue = 0
                                        }
                                    }

                                    override fun onFailure(call: Call<VoteResponse>, t: Throwable) {
                                        Log.e("onFailure3", "error: ${t.message}")
                                        t.stackTrace
//                                        upClicked = false
                                    }

                                })
                            } else if (currentMiddleValue == 1) {
                                middleVoteCall.enqueue(object : Callback<VoteResponse> {
                                    override fun onResponse(
                                        call: Call<VoteResponse>,
                                        response: Response<VoteResponse>,
                                    ) {
                                        if (response.code() == 200) {
                                            binding.tvNotKnow.text = response.body()?.value.toString()
                                            middleClicked = false

                                            currentMiddleValue = 0
                                        }
                                    }

                                    override fun onFailure(call: Call<VoteResponse>, t: Throwable) {
                                        Log.e("onFailure3", "error: ${t.message}")
                                        t.stackTrace
//                                        upClicked = false
                                    }
                                })
                            }
                        } else {
                            binding.tvDislikes.text = response.body()?.value.toString()
                            downClicked = true

                            currentDownValue = 0

                            Log.i("onResponse", "currentDownValue: $currentDownValue upClicked: $upClicked middleClicked: $middleClicked downClicked: $downClicked")
                        }
                    }
                }

                override fun onFailure(call: Call<VoteResponse>, t: Throwable) {
//                    downClicked = false
                    t.stackTrace
                    Log.e("onFailure", "error: ${t.message}")
                }
            })
        }
    }
}
