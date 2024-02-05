package com.congressdatacontest.congressdatacontest_android.feature.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.congressdatacontest.congressdatacontest_android.databinding.ItemBillBinding

class BillAdapter(
    private val onClick: (BillResponseItem) -> Unit
): ListAdapter<BillResponseItem, BillAdapter.BillViewHolder>(BillEvaluationProcessDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BillViewHolder {
        val binding =
            ItemBillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BillViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        val billEvaluationProcess = getItem(position)
        holder.bind(billEvaluationProcess)
    }

    class BillViewHolder(
        private val binding: ItemBillBinding,
        private val onClick: (BillResponseItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bill: BillResponseItem) {
            binding.root.setOnClickListener {
                onClick(bill)
            }

            binding.tvProcess.text = bill.status
            binding.tvTitleBill.text = bill.title
            binding.tvBillSuggester.text = bill.proposer
            binding.tvBillSuggestDate.text = bill.registerDate
            binding.tvBillParentCategory.text = bill.majorTagName
            binding.tvBillSubCategory.text = bill.minorTagName
        }
    }

    companion object BillEvaluationProcessDiffUtil : DiffUtil.ItemCallback<BillResponseItem>() {
        override fun areItemsTheSame(oldItem: BillResponseItem, newItem: BillResponseItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: BillResponseItem, newItem: BillResponseItem): Boolean {
            return oldItem == newItem
        }
    }
}
