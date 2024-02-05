package com.congressdatacontest.congressdatacontest_android.feature.category

data class Category(val name: String, val subcategories: List<Subcategory>, val color: String)
data class Subcategory(val id: Int, val name: String, var color: String = "#FF0000", var isSelected: Boolean = false,) {
    fun toggleSelected() {
        isSelected = !isSelected
    }
}
