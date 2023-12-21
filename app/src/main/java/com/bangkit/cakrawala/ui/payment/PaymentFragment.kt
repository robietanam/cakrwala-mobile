package com.bangkit.cakrawala.ui.payment

import android.content.Intent
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
import com.bangkit.cakrawala.data.response.TransactionHistoryResponseItem
import com.bangkit.cakrawala.databinding.FragmentPaymentBinding
import com.bangkit.cakrawala.ui.AuthViewModelFactory
import com.bangkit.cakrawala.ui.TransactionHistoryViewModelFactory
import com.bangkit.cakrawala.ui.common.TokenViewModel
import com.bangkit.cakrawala.ui.history.HistoryAdapter
import com.bangkit.cakrawala.ui.login.LoginActivity

class PaymentFragment : Fragment() {

    companion object {
        fun newInstance() = PaymentFragment()
    }

    private lateinit var viewModel: PaymentViewModel
    private lateinit var binding: FragmentPaymentBinding
    private lateinit var adapter: PaymentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, TransactionHistoryViewModelFactory.getInstance(requireContext()))[PaymentViewModel::class.java]

        val tokenViewModel = ViewModelProvider(this, AuthViewModelFactory.getInstance(requireContext()))[TokenViewModel::class.java]

        tokenViewModel.getToken().observe(viewLifecycleOwner){
            if (it.premium != null && it.premium > 0) {
                binding.tvPremiumDesc.text = "Anda berlangganan paket premium ${it.premium}"
                binding.btnBeli.visibility = View.INVISIBLE
            }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvTransactions.layoutManager = layoutManager

        adapter = PaymentAdapter()
        getData()
        binding.rvTransactions.adapter = adapter

        binding.srlRefreshTransactions.setOnRefreshListener {
            getData()
            binding.srlRefreshTransactions.isRefreshing = true;
            adapter.refresh()
        }

        viewModel.buyPremium.observe( viewLifecycleOwner){
            if(it.data != null) {
                val intent = Intent(requireContext(), PaymentActivity::class.java)
                intent.putExtra(PaymentActivity.TAG, it.data!!.redirectUrl)
                startActivity(intent)
            }
        }

        binding.btnBeli.setOnClickListener {
            beliPremium()
        }


    }

    private fun beliPremium(){
        viewModel.buyPremium(1)
    }

    private fun getData(){
        viewModel.getPaging.observe(viewLifecycleOwner){
            binding.srlRefreshTransactions.isRefreshing = false;
            if (it != null){
                setHistory(it)
            } else {
                //AuthHelper.logOut(this, tokenViewModel)
            }
        }
    }

    private fun setHistory(story: PagingData<TransactionHistoryResponseItem>){

        adapter.submitData(lifecycle,story)

        binding.textView18.text = if (adapter.itemCount == 0 )"Tidak ada riwayat transaksi" else "Transaksi"

        adapter.setOnItemClickCallback(object : PaymentAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TransactionHistoryResponseItem) {

            }
        })
    }

}