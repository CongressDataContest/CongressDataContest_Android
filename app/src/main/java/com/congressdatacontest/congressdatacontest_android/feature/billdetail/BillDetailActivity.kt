package com.congressdatacontest.congressdatacontest_android.feature.billdetail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.congressdatacontest.congressdatacontest_android.databinding.ActivityBillDetailBinding
import com.congressdatacontest.congressdatacontest_android.feature.board.Bill

class BillDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBillDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBillDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListeners()
    }

//    private fun getBillData(): Bill? {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            intent.getParcelableExtra("bill", Bill::class.java)
//        } else {
//            @Suppress("DEPRECATION")
//            intent.getParcelableExtra("bill")
//        }
//    }

    private fun initView() {
//        binding.tvBillSuggesterValue.text = getBillData()?.suggester
//        binding.tvBillSuggesterDateValue.text = getBillData()?.date
//        binding.tvBillStatusValue.text = getBillData()?.status
//        binding.tvBillDetailContentValue.text = getBillData()?.content
//        binding.tvBillParentCategory.text = getBillData()?.majorTagName
//        binding.tvBillSubCategory.text = getBillData()?.minorTagName
//        binding.tvLikes.text = getBillData()?.likes.toString()
//        binding.tvNotKnow.text = getBillData()?.noResponses.toString()
//        binding.tvDislikes.text = getBillData()?.dislikes.toString()
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}
