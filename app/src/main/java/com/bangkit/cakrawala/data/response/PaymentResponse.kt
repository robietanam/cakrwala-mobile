package com.bangkit.cakrawala.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class BuyPremiumResponse(

    @field:SerializedName("status") var status: String? = null,
    @field:SerializedName("message") var message: String? = null,
    @field:SerializedName("data") var data: BuyPremiumResponseItem? = BuyPremiumResponseItem()

)


data class BuyPremiumResponseItem(

    @field:SerializedName("order_id") var orderId: Int? = null,
    @field:SerializedName("redirect_url") var redirectUrl: String? = null,
    @field:SerializedName("token") var token: String? = null

)

data class PremiumResponse(

    @field:SerializedName("status") var status: String? = null,
    @field:SerializedName("message") var message: String? = null,
    @field:SerializedName("data") var data: ArrayList<PremiumResponseItem> = arrayListOf()

)


data class PremiumResponseItem(

    @field:SerializedName("id") var id: Int? = null,
    @field:SerializedName("nama_paket") var namaPaket: String? = null,
    @field:SerializedName("harga_paket") var hargaPaket: Int? = null,
    @field:SerializedName("durasi") var durasi: Int? = null
)


data class TransactionHistoryResponse(

    @field:SerializedName("status") var status: String? = null,
    @field:SerializedName("message") var message: String? = null,
    @field:SerializedName("data") var data: ArrayList<TransactionHistoryResponseItem> = arrayListOf()
)

@Entity(tableName = "transaction")
data class TransactionHistoryResponseItem(

    @PrimaryKey
    @field:SerializedName("id") var id: String,
    @field:SerializedName("premium_id") var premiumId: Int? = null,
    @field:SerializedName("user_id") var userId: Int? = null,
    @field:SerializedName("url_payment") var urlPayment: String? = null,
    @field:SerializedName("created_at") var createdAt: String? = null,
    @field:SerializedName("transaction_time") var transactionTime: String? = null,
    @field:SerializedName("transaction_status") var transactionStatus: String? = null,
    @field:SerializedName("transaction_id") var transactionId: String? = null,
    @field:SerializedName("status_message") var statusMessage: String? = null,
    @field:SerializedName("status_code") var statusCode: String? = null,
    @field:SerializedName("signature_key") var signatureKey: String? = null,
    @field:SerializedName("payment_type") var paymentType: String? = null,
    @field:SerializedName("merchant_id") var merchantId: String? = null,
    @field:SerializedName("gross_amount") var grossAmount: String? = null,
    @field:SerializedName("fraud_status") var fraudStatus: String? = null,
    @field:SerializedName("currency") var currency: String? = null

)