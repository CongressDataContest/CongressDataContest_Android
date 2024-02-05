package com.congressdatacontest.congressdatacontest_android.feature.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.congressdatacontest.congressdatacontest_android.databinding.ItemBillBinding

class BillAdapter(
    private val onClick: (Bill) -> Unit
): ListAdapter<Bill, BillAdapter.BillViewHolder>(BillEvaluationProcessDiffUtil) {

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
        private val onClick: (Bill) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bill: Bill) {
            binding.root.setOnClickListener {
                onClick(bill)
            }

            binding.tvProcess.text = bill.billInfo.status
            binding.tvTitleBill.text = bill.billInfo.title
            binding.tvBillSuggester.text = bill.billInfo.proposer
            binding.tvBillSuggestDate.text = bill.billInfo.registerDate
            binding.tvBillParentCategory.text = bill.billInfo.majorTagName
            binding.tvBillSubCategory.text = bill.billInfo.minorTagName
        }
    }

    companion object BillEvaluationProcessDiffUtil : DiffUtil.ItemCallback<Bill>() {
        override fun areItemsTheSame(oldItem: Bill, newItem: Bill): Boolean {
            return oldItem.billInfo.title == newItem.billInfo.title
        }

        override fun areContentsTheSame(oldItem: Bill, newItem: Bill): Boolean {
            return oldItem == newItem
        }
    }
}
