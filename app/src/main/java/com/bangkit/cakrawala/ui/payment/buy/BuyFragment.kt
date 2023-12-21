package com.bangkit.cakrawala.ui.payment.buy

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.cakrawala.R

class BuyFragment : Fragment() {

    companion object {
        fun newInstance() = BuyFragment()
    }

    private lateinit var viewModel: BuyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_buy, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BuyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}