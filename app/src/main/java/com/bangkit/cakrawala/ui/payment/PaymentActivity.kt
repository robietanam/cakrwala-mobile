package com.bangkit.cakrawala.ui.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.cakrawala.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPaymentBinding
    companion object {
        const val TAG = "PAYMENT_ACTIVITY"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val urlPayment = intent.getStringExtra(TAG)

        binding.wvPayment.apply {
            settings.javaScriptEnabled = true
            loadUrl(urlPayment!!)
        }
    }
}