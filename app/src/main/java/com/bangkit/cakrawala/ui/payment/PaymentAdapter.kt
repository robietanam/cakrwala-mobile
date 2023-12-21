package com.bangkit.cakrawala.ui.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.cakrawala.data.response.TransactionHistoryResponseItem
import com.bangkit.cakrawala.databinding.ItemRowHistoryBinding
import com.bangkit.cakrawala.databinding.ItemRowTransactionHistoryBinding
import com.bangkit.cakrawala.ui.history.HistoryExpandableListAdapter
import com.bangkit.cakrawala.utils.stringToDate


class PaymentAdapter :
    PagingDataAdapter<TransactionHistoryResponseItem, PaymentAdapter.MyViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TransactionHistoryResponseItem>() {
            override fun areItemsTheSame(
                oldItem: TransactionHistoryResponseItem,
                newItem: TransactionHistoryResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: TransactionHistoryResponseItem,
                newItem: TransactionHistoryResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: TransactionHistoryResponseItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    private lateinit var binding: ItemRowTransactionHistoryBinding

    inner class MyViewHolder(private val binding: ItemRowTransactionHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: TransactionHistoryResponseItem) {

            binding.tvTransactionCreatedAt.text = history.createdAt?.let { stringToDate(it) }
            binding.tvOrderId.text = history.id
            binding.tvTransactionStatus.text = if(!history.transactionStatus.isNullOrEmpty() && history.transactionStatus == "settlement") "Sukses" else "Gagal"

            binding.cardViewHistory.setOnClickListener {
                onItemClickCallback.onItemClicked(history)
            }
        }
    }


    override fun onBindViewHolder(holder: PaymentAdapter.MyViewHolder, position: Int) {
        val history = getItem(position)

        if (history != null) {
            holder.bind(history)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentAdapter.MyViewHolder {
        binding = ItemRowTransactionHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

}