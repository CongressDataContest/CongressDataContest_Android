package com.congressdatacontest.congressdatacontest_android.feature.category

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
            if (!isSelectedSubcategoryExists)
                Toast.makeText(this, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, BoardActivity::class.java)
            startActivity(intent)
        }
    }
}

fun getSelectedSubcategories(categories: List<Category>): MutableList<Triple<Int, String, String>> {
    return categories.flatMap { category ->
        category.subcategories.filter { it.isSelected }.map { subcategory ->
            Triple(subcategory.id, subcategory.name, subcategory.color)
        }
    }.toMutableList()
}

fun getSelectedSubcategoryIds(categories: List<Category>): List<Int> {
    return categories.flatMap { category ->
        category.subcategories.filter { it.isSelected }.map { subcategory ->
            subcategory.id
        }
    }
}

val isSelectedSubcategoryExists = categories.any { category ->
    category.subcategories.any { subcategory ->
        subcategory.isSelected
    }
}


