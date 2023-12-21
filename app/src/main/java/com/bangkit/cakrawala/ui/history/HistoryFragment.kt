package com.bangkit.cakrawala.ui.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.cakrawala.R
import com.bangkit.cakrawala.data.response.HistoryResponseItem
import com.bangkit.cakrawala.databinding.FragmentHistoryBinding
import com.bangkit.cakrawala.databinding.FragmentTextDetectionBinding
import com.bangkit.cakrawala.ui.AuthViewModelFactory
import com.bangkit.cakrawala.ui.HistoryViewModelFactory
import com.bangkit.cakrawala.ui.login.LoginViewModel

class HistoryFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: HistoryViewModel
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, HistoryViewModelFactory.getInstance(requireContext()))[HistoryViewModel::class.java]


        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistories.layoutManager = layoutManager

        adapter = HistoryAdapter()
        getData()
        binding.rvHistories.adapter = adapter

        binding.srlRefresh.setOnRefreshListener {
            getData()
            binding.srlRefresh.isRefreshing = true;
            adapter.refresh()
        }

    }

    private fun getData(){
        viewModel.getPaging.observe(viewLifecycleOwner){
            binding.srlRefresh.isRefreshing = false;
            if (it != null){
                setHistory(it)
            } else {
            //AuthHelper.logOut(this, tokenViewModel)
            }
        }
    }

    private fun setHistory(story: PagingData<HistoryResponseItem>){

        adapter.submitData(lifecycle,story)

        adapter.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: HistoryResponseItem) {

            }
        })
    }

}