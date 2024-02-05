package com.congressdatacontest.congressdatacontest_android.feature.board

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.congressdatacontest.congressdatacontest_android.R
import com.congressdatacontest.congressdatacontest_android.common.BoardService
import com.congressdatacontest.congressdatacontest_android.common.RetrofitClient
import com.congressdatacontest.congressdatacontest_android.databinding.ActivityBoardBinding
import com.congressdatacontest.congressdatacontest_android.feature.billdetail.BillDetailActivity
import com.congressdatacontest.congressdatacontest_android.feature.category.categories
import com.congressdatacontest.congressdatacontest_android.feature.category.getSelectedSubcategories
import com.congressdatacontest.congressdatacontest_android.feature.category.getSelectedSubcategoryIds
import com.congressdatacontest.congressdatacontest_android.feature.category.getSubcategoryUnClickColor
import com.google.android.material.chip.Chip
import retrofit2.Call
import retrofit2.Response

class BoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardBinding
    private lateinit var billAdapter: BillAdapter

    private var getBoardService: BoardService = RetrofitClient.provideRetrofit().create(
        BoardService::class.java
    )

    private var searchRequest = SearchRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initProgressType()
        initSortedType()
        initTagList()
        initRecyclerView()
        initBackButton()
        initTextInputEditText()

        getBoard()
    }

    private fun initTextInputEditText() {
        binding.textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // before
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // changed
                searchRequest.searchKeyword = s.toString()
                getBoardFiltering()
            }

            override fun afterTextChanged(s: Editable?) {
                // after
            }
        })
    }

    private fun initBackButton() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initTagList() {
        val items = getSelectedSubcategories(categories)

        searchRequest.tagId = getSelectedSubcategoryIds(categories)

        items.forEach { (id, name, color) ->
            val chip = Chip(this).apply {
                text = name
                isCloseIconVisible = true
                chipBackgroundColor =
                    ColorStateList.valueOf(Color.parseColor(getSubcategoryUnClickColor(color)))
                setOnCloseIconClickListener {
                    binding.chipGroup.removeView(this)
                    toggleSelectedSubcategory(id)
                    searchRequest.tagId = getSelectedSubcategoryIds(categories)
                    getBoardFiltering()
                }
                chipStrokeColor =
                    ColorStateList.valueOf(Color.parseColor(getSubcategoryUnClickColor(color)))
            }
            binding.chipGroup.addView(chip)
        }
    }

    private fun initSortedType() {
        val itemSort = listOf("최신순", "인기순")
        val sortAdapter = ArrayAdapter(this, R.layout.item_dropdown_menu_popup, itemSort)

        binding.sortType.setAdapter(sortAdapter)

        binding.sortType.setOnItemClickListener { adapterView, view, position, id ->
            val selectedItem = adapterView.getItemAtPosition(position).toString()

            if (selectedItem == "최신순")
                searchRequest.orderCondition = "LATEST"
            else if (selectedItem == "인기순")
                searchRequest.orderCondition = "POPULARITY"

            getBoardFiltering()
        }

        binding.sortType.setText(sortAdapter.getItem(0), false)
    }

    private fun initProgressType() {
        val itemProgress = listOf("전체", "접수", "위원회심사", "체계자구심사", "본회의심의", "정부이송", "공포 ")
        val progressAdapter = ArrayAdapter(this, R.layout.item_dropdown_menu_popup, itemProgress)

        binding.progressType.setAdapter(progressAdapter)

        binding.progressType.setOnItemClickListener { adapterView, view, position, id ->
            val selectedItem = adapterView.getItemAtPosition(position).toString()
            searchRequest.status = selectedItem
            getBoardFiltering()
        }

        binding.progressType.setText(progressAdapter.getItem(0), false)
    }

    private fun getBoard() {
        val call = getBoardService.getBoard(TagRequest(getSelectedSubcategoryIds(categories)))

        call.enqueue(object : retrofit2.Callback<List<BillResponseItem>> {
            override fun onResponse(call: Call<List<BillResponseItem>>, response: Response<List<BillResponseItem>>) {
                Log.d("board", "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<List<BillResponseItem>>, t: Throwable) {
                Log.e("onFailure", "error: ${t.message}")
                t.stackTrace
            }
        })
    }

    private fun getBoardFiltering() {
        val call = getBoardService.getBoardFiltering(searchRequest)

        call.enqueue(object : retrofit2.Callback<List<BillResponseItem>> {
            override fun onResponse(call: Call<List<BillResponseItem>>, response: Response<List<BillResponseItem>>) {
                Log.d("search", "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<List<BillResponseItem>>, t: Throwable) {
                Log.e("onFailure2", "error: ${t.message}")
                t.stackTrace
            }
        })
    }

    private fun initRecyclerView() {
        billAdapter = BillAdapter(
            onClick = ::onClickBill
        )

        binding.rvBillList.run {
            adapter = billAdapter
            layoutManager = LinearLayoutManager(this@BoardActivity)
        }
    }

    private fun onClickBill(bill: Bill) {
        val intent = Intent(this, BillDetailActivity::class.java)
        intent.putExtra("bill", bill)
        startActivity(intent)
    }

    private fun toggleSelectedSubcategory(id: Int) {
        categories.forEach { category ->
            category.subcategories.forEach { subcategory ->
                if (subcategory.id == id) {
                    subcategory.toggleSelected()
                }
            }
        }
    }
}
