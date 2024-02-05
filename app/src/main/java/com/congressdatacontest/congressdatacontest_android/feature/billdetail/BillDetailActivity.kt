package com.congressdatacontest.congressdatacontest_android.feature.billdetail

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
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

        // 뉴스가 0개 일 때
        if (newsAdapter.currentList.isEmpty()) {
            binding.rvNewsList.isGone = true
            binding.rvNewsList.isVisible = false
        } else { // 뉴스가 1 ~ 3개일 때
            binding.rvNewsList.isGone = false
            binding.rvNewsList.isVisible = true
        }

        Log.i("initView", "NewsList: ${newsAdapter.currentList}," +
                "isGone = ${binding.rvNewsList.isGone}, " +
                "isVisible = ${binding.rvNewsList.isVisible}")

        Log.i("initView", "BillData: ${getBillData()}, " +
                "billId: ${getBillData()?.id}")

        val call = getBillDetailService.getBillDetail(getBillData()?.id!!)

        Log.i("initView", "call: $call")

        call.enqueue(object : Callback<Bill> {
            override fun onResponse(call: Call<Bill>, response: Response<Bill>) {
                if (response.code() == 200) {
                    Log.i("onResponse", "response: ${response.body()}")
                    binding.tvBillSuggesterValue.text = response.body()?.billInfo?.proposer
                    binding.tvBillSuggesterDateValue.text = response.body()?.billInfo?.registerDate
                    binding.tvBillStatusValue.text = response.body()?.billInfo?.status
                    binding.tvBillDetailContentValue.text = response.body()?.billInfo?.pageContent
                    binding.tvBillParentCategory.text = response.body()?.billInfo?.majorTagName
                    binding.tvBillSubCategory.text = response.body()?.billInfo?.minorTagName
                    binding.tvLikes.text = response.body()?.billInfo?.upVoteCount.toString()
                    binding.tvNotKnow.text = response.body()?.billInfo?.middleVoteCount.toString()
                    binding.tvDislikes.text = response.body()?.billInfo?.downVoteCount.toString()

                    when (response.body()?.billInfo?.status) {
                        Status.ONE.status -> {
                            binding.billEvaluationProcess01.cvProcess.setBackgroundColor(
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
                            binding.billEvaluationProcess01.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess02.cvProcess.setBackgroundColor(
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
                            binding.billEvaluationProcess01.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess02.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess03.cvProcess.setBackgroundColor(
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
                            binding.billEvaluationProcess01.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess02.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess03.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess04.cvProcess.setBackgroundColor(
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
                            binding.billEvaluationProcess01.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess02.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess03.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess04.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess05.cvProcess.setBackgroundColor(
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
                            binding.billEvaluationProcess01.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess02.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess03.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess04.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess05.cvProcess.setBackgroundColor(
                                ContextCompat.getColor(this@BillDetailActivity, R.color.billProcessed))
                            binding.billEvaluationProcess06.cvProcess.setBackgroundColor(
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
                Log.e("onFailure", "error: ${t.message}")
                t.stackTrace
            }
        })
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        newsAdapter = NewsAdapter(
            onClick = ::onClickNewsItem
        )

        recyclerView.run {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@BillDetailActivity)
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
            // 이미 up을 클릭했으면 -> -1
            // up을 클린한 적이 없으면 -> 1
            // middle 또는 down을 클릭한 상태이면 -> 1

            val myCount: Int = if (upClicked) {
                -1
            } else if (middleClicked && downClicked) {
                1
            } else {
                1
            }

            Log.i("layoutLike", "upClicked: $upClicked, " +
                    "middleClicked: $middleClicked, " +
                    "downClicked: $downClicked, " +
                    "myCount: $myCount")

            val call = voteBillService.voteBill(
                VoteRequest(
                    getBillData()?.id!!,
                    Vote.UP.vote,
                    myCount
                )
            )

            call.enqueue(object : Callback<VoteResponse> {
                override fun onResponse(
                    call: Call<VoteResponse>,
                    response: Response<VoteResponse>,
                ) {
                    if (response.code() == 200) {
                        binding.tvLikes.text = response.body()?.value.toString()
                        upClicked = true
                    }
                }

                override fun onFailure(call: Call<VoteResponse>, t: Throwable) {
                    Log.e("onFailure", "error: ${t.message}")
                    t.stackTrace
                    upClicked = false
                }
            })
        }

        binding.layoutNoResponse.setOnClickListener {
            // 이미 middle을 클릭했으면 -> -1
            // middle을 클린한 적이 없으면 -> 1
            // up 또는 down을 클릭한 상태이면 -> 1
            val myCount: Int = if (middleClicked) {
                -1
            } else if (upClicked && downClicked) {
                1
            } else {
                1
            }

            Log.i("layoutNoResponse", "middleClicked: $middleClicked, " +
                    "upClicked: $upClicked, " +
                    "downClicked: $downClicked, " +
                    "myCount: $myCount")

            val call = voteBillService.voteBill(
                VoteRequest(
                    getBillData()?.id!!,
                    Vote.MIDDLE.vote,
                    myCount
                )
            )

            call.enqueue(object : Callback<VoteResponse> {
                override fun onResponse(
                    call: Call<VoteResponse>,
                    response: Response<VoteResponse>,
                ) {
                    if (response.code() == 200) {
                        binding.tvNotKnow.text = response.body()?.value.toString()
                        middleClicked = true
                    }
                }

                override fun onFailure(call: Call<VoteResponse>, t: Throwable) {
                    Log.e("onFailure", "error: ${t.message}")
                    t.stackTrace
                    middleClicked = false
                }
            })
        }

        binding.layoutDislike.setOnClickListener {
            // 이미 down을 클릭했으면 -> -1
            // down을 클린한 적이 없으면 -> 1
            // up 또는 middle을 클릭한 상태이면 -> 1
            val myCount: Int = if (downClicked) {
                -1
            } else if (upClicked && middleClicked) {
                1
            } else {
                1
            }

            Log.i("layoutDislike", "downClicked: $downClicked, " +
                    "upClicked: $upClicked, " +
                    "middleClicked: $middleClicked, " +
                    "myCount: $myCount")

            val call = voteBillService.voteBill(
                VoteRequest(
                    getBillData()?.id!!,
                    Vote.DOWN.vote,
                    myCount
                )
            )

            call.enqueue(object : Callback<VoteResponse> {
                override fun onResponse(
                    call: Call<VoteResponse>,
                    response: Response<VoteResponse>,
                ) {
                    if (response.code() == 200) {
                        binding.tvDislikes.text = response.body()?.value.toString()
                        downClicked = true
                    }
                }

                override fun onFailure(call: Call<VoteResponse>, t: Throwable) {
                    downClicked = false
                    t.stackTrace
                    Log.e("onFailure", "error: ${t.message}")
                }
            })
        }
    }
}
