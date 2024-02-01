package com.congressdatacontest.congressdatacontest_android.feature.category

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.congressdatacontest.congressdatacontest_android.databinding.ItemSubcategoryBinding

class SubcategoryAdapter(private val subcategories: List<Subcategory>) :
    RecyclerView.Adapter<SubcategoryAdapter.SubcategoryViewHolder>() {

    // View Binding for the Subcategory items
    inner class SubcategoryViewHolder(private val binding: ItemSubcategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(subcategory: Subcategory) {
            binding.subcategory = subcategory
            updateButtonColor(subcategory)

            binding.buttonSubcategory.setOnClickListener {
                // Toggle the selection status
                subcategory.toggleSelected()
                updateButtonColor(subcategory)
            }
        }

        private fun updateButtonColor(subcategory: Subcategory) {
            val color = if (subcategory.isSelected) subcategory.color else getSubcategoryUnClickColor(subcategory.color)
            binding.buttonSubcategory.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSubcategoryBinding.inflate(inflater, parent, false)
        return SubcategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubcategoryViewHolder, position: Int) {
        holder.bind(subcategories[position])
    }

    override fun getItemCount(): Int = subcategories.size

    fun selectAllSubcategories() {
        subcategories.forEach { it.isSelected = true }
        notifyDataSetChanged()
    }
}