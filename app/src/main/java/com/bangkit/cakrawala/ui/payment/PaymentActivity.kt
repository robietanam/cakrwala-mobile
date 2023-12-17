package com.bangkit.cakrawala.ui.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.midtrans.sdk.corekit.core.PaymentMethod
import com.midtrans.sdk.uikit.api.model.*
import com.midtrans.sdk.uikit.external.UiKitApi
import com.midtrans.sdk.uikit.internal.util.UiKitConstants
import com.midtrans.sdk.uikit.internal.util.UiKitConstants.STATUS_CANCELED
import com.midtrans.sdk.uikit.internal.util.UiKitConstants.STATUS_FAILED
import com.midtrans.sdk.uikit.internal.util.UiKitConstants.STATUS_INVALID
import com.midtrans.sdk.uikit.internal.util.UiKitConstants.STATUS_PENDING
import com.midtrans.sdk.uikit.internal.util.UiKitConstants.STATUS_SUCCESS
import com.bangkit.cakrawala.databinding.ActivityPaymentBinding
import com.bangkit.cakrawala.utils.MERCHANT_URL
import java.util.UUID

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        buildUiKit()

        mbinding.buttonUikit.setOnClickListener {
            UiKitApi.getDefaultInstance().startPaymentUiFlow(
                activity = this,
                launcher = launcher,
                transactionDetails = initTransactionDetails(),
                customerDetails = customerDetails,
                itemDetails = itemDetails
            )
        }
        mbinding.buttonDirectCreditCard.setOnClickListener {
            UiKitApi.getDefaultInstance().startPaymentUiFlow(
                activity = this,
                launcher = launcher,
                transactionDetails = initTransactionDetails(),
                customerDetails = customerDetails,
                itemDetails = itemDetails,
                paymentMethod = PaymentMethod.CREDIT_CARD
            )
        }
        mbinding.buttonDirectBcaVa.setOnClickListener {
            UiKitApi.getDefaultInstance().startPaymentUiFlow(
                activity = this,
                launcher = launcher,
                transactionDetails = initTransactionDetails(),
                customerDetails = customerDetails,
                itemDetails = itemDetails,
                paymentMethod = PaymentMethod.BANK_TRANSFER_BCA
            )
        }
        mbinding.buttonDirectBniVa.setOnClickListener {
            UiKitApi.getDefaultInstance().startPaymentUiFlow(
                activity = this,
                launcher = launcher,
                transactionDetails = initTransactionDetails(),
                customerDetails = customerDetails,
                itemDetails = itemDetails,
                paymentMethod = PaymentMethod.BANK_TRANSFER_BNI
            )
        }
        mbinding.buttonDirectMandiriVa.setOnClickListener {
            UiKitApi.getDefaultInstance().startPaymentUiFlow(
                activity = this,
                launcher = launcher,
                transactionDetails = initTransactionDetails(),
                customerDetails = customerDetails,
                itemDetails = itemDetails,
                paymentMethod = PaymentMethod.BANK_TRANSFER_MANDIRI
            )
        }
        mbinding.buttonDirectPermataVa.setOnClickListener {
            UiKitApi.getDefaultInstance().startPaymentUiFlow(
                activity = this,
                launcher = launcher,
                transactionDetails = initTransactionDetails(),
                customerDetails = customerDetails,
                itemDetails = itemDetails,
                paymentMethod = PaymentMethod.BANK_TRANSFER_PERMATA
            )
        }
        mbinding.buttonDirectAtmBersamaVa.setOnClickListener {
            UiKitApi.getDefaultInstance().startPaymentUiFlow(
                activity = this,
                launcher = launcher,
                transactionDetails = initTransactionDetails(),
                customerDetails = customerDetails,
                itemDetails = itemDetails,
                paymentMethod = PaymentMethod.BANK_TRANSFER_OTHER
            )
        }
        mbinding.buttonSnapPay.setOnClickListener {
            UiKitApi.getDefaultInstance().startPaymentUiFlow(
                activity = this,
                launcher = launcher,
                snapToken = mbinding.editSnaptoken.editableText.toString()
            )
        }
    }

    private fun buildUiKit() {
        UiKitApi.Builder()
            .withContext(this.applicationContext)
            .withMerchantUrl(MERCHANT_URL)
            .withMerchantClientKey("SB-Mid-client-w4K5mFi4-qNAD-_5")
            .enableLog(true)
            .withColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
            .build()
        uiKitCustomSetting()
    }

    private fun uiKitCustomSetting() {
        val uIKitCustomSetting = UiKitApi.getDefaultInstance().uiKitSetting
        uIKitCustomSetting.saveCardChecked = true
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == RESULT_OK) {
                result.data?.let {
                    val transactionResult = it.getParcelableExtra<TransactionResult>(UiKitConstants.KEY_TRANSACTION_RESULT)
                    Toast.makeText(this, "${transactionResult?.transactionId}", Toast.LENGTH_LONG).show()
                }
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val transactionResult = data?.getParcelableExtra<TransactionResult>(UiKitConstants.KEY_TRANSACTION_RESULT)
            if (transactionResult != null) {
                when (transactionResult.status) {
                    STATUS_SUCCESS -> {
                        Toast.makeText(this, "Transaction Finished. ID: " + transactionResult.transactionId, Toast.LENGTH_LONG).show()
                    }
                    STATUS_PENDING -> {
                        Toast.makeText(this, "Transaction Pending. ID: " + transactionResult.transactionId, Toast.LENGTH_LONG).show()
                    }
                    STATUS_FAILED -> {
                        Toast.makeText(this, "Transaction Failed. ID: " + transactionResult.transactionId, Toast.LENGTH_LONG).show()
                    }
                    STATUS_CANCELED -> {
                        Toast.makeText(this, "Transaction Cancelled", Toast.LENGTH_LONG).show()
                    }
                    STATUS_INVALID -> {
                        Toast.makeText(this, "Transaction Invalid. ID: " + transactionResult.transactionId, Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(this, "Transaction ID: " + transactionResult.transactionId + ". Message: " + transactionResult.status, Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Transaction Invalid", Toast.LENGTH_LONG).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private lateinit var mbinding: ActivityPaymentBinding

    private var customerDetails = CustomerDetails(
        firstName = "BROWTF",
        customerIdentifier = "akumail@mail.com",
        email = "akumail@mail.com",
        phone = "0853101020210"
    )
    private var itemDetails = listOf(ItemDetails("test-01", 36500.0, 1, "test01"))

    private fun initTransactionDetails() : SnapTransactionDetail {
        return SnapTransactionDetail(
            orderId = UUID.randomUUID().toString(),
            grossAmount = 36500.0
        )
    }
}