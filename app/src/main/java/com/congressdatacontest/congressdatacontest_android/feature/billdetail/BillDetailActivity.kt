package com.congressdatacontest.congressdatacontest_android.feature.billdetail

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
import com.congressdatacontest.congressdatacontest_android.databinding.ActivityBillDetailBinding
import com.congressdatacontest.congressdatacontest_android.feature.board.Bill
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBillDetailBinding

    private var getBillDetailService: GetBillDetailService = RetrofitClient.provideRetrofit().create(GetBillDetailService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBillDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListeners()
    }

    private fun getBillData(): Bill? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("bill", Bill::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("bill")
        }
    }

    private fun initView() {
        Log.i("initView", "BillData: ${getBillData()}, " +
                "billInfo: ${getBillData()?.billInfo}, " +
                "billId: ${getBillData()?.billInfo?.id}")

        val call = getBillDetailService.getBillDetail(getBillData()?.billInfo?.id!!)

        call.enqueue(object : Callback<Bill> {
            override fun onResponse(call: Call<Bill>, response: Response<Bill>) {
                if (response.code() == 200) {
                    binding.tvBillSuggesterValue.text = getBillData()?.billInfo?.proposer
                    binding.tvBillSuggesterDateValue.text = getBillData()?.billInfo?.registerDate
                    binding.tvBillStatusValue.text = getBillData()?.billInfo?.status
                    binding.tvBillDetailContentValue.text = getBillData()?.billInfo?.pageContent
                    binding.tvBillParentCategory.text = getBillData()?.billInfo?.majorTagName
                    binding.tvBillSubCategory.text = getBillData()?.billInfo?.minorTagName
                    binding.tvLikes.text = getBillData()?.billInfo?.upVoteCount.toString()
                    binding.tvNotKnow.text = getBillData()?.billInfo?.middleVoteCount.toString()
                    binding.tvDislikes.text = getBillData()?.billInfo?.downVoteCount.toString()

                    when (getBillData()?.billInfo?.status) {
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

    private fun initListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}
