package com.congressdatacontest.congressdatacontest_android.feature.category

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.congressdatacontest.congressdatacontest_android.databinding.ItemCategoryBinding

class CategoryAdapter(private val categories: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    // View Binding for the Category items
    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category = category
            binding.linearLayoutCategory.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(getSubcategoryColor(category.color)))
            val subcategoryAdapter = SubcategoryAdapter(category.subcategories)
            binding.recyclerViewSubCategory.layoutManager =
                LinearLayoutManager(binding.root.context)
            binding.recyclerViewSubCategory.adapter = subcategoryAdapter

            binding.all.setOnClickListener {
                subcategoryAdapter.selectAllSubcategories()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size
}
