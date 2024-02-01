package com.congressdatacontest.congressdatacontest_android.feature.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.congressdatacontest.congressdatacontest_android.databinding.ActivityMainBinding
import com.congressdatacontest.congressdatacontest_android.feature.board.BoardActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 대분류 및 소분류 데이터를 초기화합니다.
        categories.forEach { category ->
            val subcategoryColor = category.color
            category.subcategories.forEach { subcategory ->
                subcategory.color = subcategoryColor
            }
        }

        categoryAdapter = CategoryAdapter(categories)
        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCategories.adapter = categoryAdapter

        binding.buttonGoToInterests.setOnClickListener {
            val intent = Intent(this, BoardActivity::class.java)
            startActivity(intent)
        }
    }
}

fun getSelectedSubcategories(categories: List<Category>): MutableList<Pair<String, String>> {
    return categories.flatMap { category ->
        category.subcategories.filter { it.isSelected }.map { subcategory ->
            Pair(subcategory.name, subcategory.color)
        }
    }.toMutableList()
}