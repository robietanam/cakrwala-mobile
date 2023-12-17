package com.bangkit.cakrawala.ui.settings

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bangkit.cakrawala.R
import com.bangkit.cakrawala.data.response.Auth
import com.bangkit.cakrawala.databinding.FragmentHomeBinding
import com.bangkit.cakrawala.databinding.FragmentSettingsBinding
import com.bangkit.cakrawala.ui.AuthViewModelFactory
import com.bangkit.cakrawala.ui.HistoryViewModelFactory
import com.bangkit.cakrawala.ui.common.TokenViewModel
import com.bangkit.cakrawala.ui.history.HistoryViewModel
import com.bangkit.cakrawala.ui.login.LoginActivity
import com.bangkit.cakrawala.ui.payment.PaymentActivity

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel
    private lateinit var tokenViewModel: TokenViewModel
    private lateinit var binding: FragmentSettingsBinding
    private var userData: Auth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        tokenViewModel = ViewModelProvider(this, AuthViewModelFactory.getInstance(requireContext()))[TokenViewModel::class.java]
        tokenViewModel.getToken().observe(viewLifecycleOwner){
            if (!it.token.isNullOrEmpty()){
                binding.settingTvNama.text = it.userName
                userData = it
            } else {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

        binding.btnPayment.setOnClickListener {
            val intent = Intent(requireContext(), PaymentActivity::class.java)
            startActivity(intent)
        }

        val historyViewModel = ViewModelProvider(this, HistoryViewModelFactory.getInstance(requireContext()))[HistoryViewModel::class.java]
        binding.logout.setOnClickListener {
            tokenViewModel.resetToken()
            historyViewModel.deleteAll()
        }

    }

}