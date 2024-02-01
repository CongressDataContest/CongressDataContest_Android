package com.congressdatacontest.congressdatacontest_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.congressdatacontest.congressdatacontest_android.databinding.ActivityBillDetailBinding

class BillDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBillDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBillDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListeners()
    }

    private fun initView() {
//        binding.tvBillSuggesterValue.text
//        binding.tvBillSuggesterDateValue.text
//        binding.tvBillStatusValue.text
//        binding.tvBillDetailContentValue.text
//        binding.tvBillParentCategory.text
//        binding.tvBillSubCategory.text
    }

    private fun initListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}