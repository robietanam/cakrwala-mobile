package com.bangkit.cakrawala.ui.history

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView.OnGroupCollapseListener
import android.widget.ExpandableListView.OnGroupExpandListener
import androidx.core.view.get
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.cakrawala.data.response.HistoryResponseItem
import com.bangkit.cakrawala.databinding.ItemRowHistoryBinding
import com.bangkit.cakrawala.utils.stringToDate


class HistoryAdapter :
    PagingDataAdapter<HistoryResponseItem, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryResponseItem>() {
            override fun areItemsTheSame(
                oldItem: HistoryResponseItem,
                newItem: HistoryResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: HistoryResponseItem,
                newItem: HistoryResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: HistoryResponseItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    private lateinit var binding: ItemRowHistoryBinding
    private var adapterHistory: HistoryExpandableListAdapter? = null

    inner class MyViewHolder(private val binding: ItemRowHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryResponseItem) {

            binding.tvHistoryId.text = history.id
            binding.tvAiPrecentage.text = String.format("%.2f", history.aiPercentage)
            binding.tvHumanPrecentage.text = String.format("%.2f", history.humanPercentage)
            binding.tvCreatedAt.text = history.createdAt?.let { stringToDate(it) }
            binding.tvResultGenerated.text = history.resultGenerated

            val TITLE = "List Detected AI Sentences"
            val expandableListDetail = HashMap<String, List<String>>()
            expandableListDetail[TITLE] = history.listAiSentences ?: listOf()

            adapterHistory = HistoryExpandableListAdapter(
                binding.root.context,
                ArrayList(expandableListDetail.keys),
                expandableListDetail
            )

            binding.listAISentences.apply {
                setAdapter(adapterHistory)
                setOnGroupExpandListener {
                    var height = 0
                    val count = expandableListDetail[TITLE]?.size ?: -1
                    if (count != -1){
                        for (i in 0 until count ) {
                            height += 110
                            height += dividerHeight
                        }
                    }
                    layoutParams.height = height + 110
                }


                setOnGroupCollapseListener {
                    layoutParams.height = 100
                }

            }

            binding.cardViewHistory.setOnClickListener {
                onItemClickCallback.onItemClicked(history)
            }
        }
    }


    override fun onBindViewHolder(holder: HistoryAdapter.MyViewHolder, position: Int) {
        val history = getItem(position)

        if (history != null) {
            holder.bind(history)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.MyViewHolder {
        binding = ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

}