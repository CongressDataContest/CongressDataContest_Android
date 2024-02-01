package com.congressdatacontest.congressdatacontest_android.feature.board

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import com.congressdatacontest.congressdatacontest_android.R
import com.congressdatacontest.congressdatacontest_android.databinding.ActivityBoardBinding
import com.congressdatacontest.congressdatacontest_android.feature.category.categories
import com.congressdatacontest.congressdatacontest_android.feature.category.getSelectedSubcategories
import com.congressdatacontest.congressdatacontest_android.feature.category.getSubcategoryUnClickColor
import com.google.android.material.chip.Chip

class BoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = getSelectedSubcategories(categories)

        val itemProgress = listOf("전체", "접수", "위원회 심사", "체계자구 심사", "본회의 심의", "정부 이송", "공포")
        val progressAdapter = ArrayAdapter(this, R.layout.item_dropdown_menu_popup, itemProgress)

        val itemSort = listOf("최신순", "인기순")
        val sortAdapter = ArrayAdapter(this, R.layout.item_dropdown_menu_popup, itemSort)

        binding.progressType.setAdapter(progressAdapter)
        binding.sortType.setAdapter(sortAdapter)

        binding.progressType.setText(progressAdapter.getItem(0), false)
        binding.sortType.setText(sortAdapter.getItem(0), false)


        items.forEach { (name, color) ->
            val chip = Chip(this).apply {
                text = name
                isCloseIconVisible = true
                chipBackgroundColor = ColorStateList.valueOf(Color.parseColor(getSubcategoryUnClickColor(color)))
                setOnCloseIconClickListener {
                    binding.chipGroup.removeView(this)
                }
                chipStrokeColor = ColorStateList.valueOf(Color.parseColor(getSubcategoryUnClickColor(color)))
            }
            binding.chipGroup.addView(chip)
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}