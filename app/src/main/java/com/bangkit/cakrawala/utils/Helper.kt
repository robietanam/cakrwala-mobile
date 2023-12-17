package com.bangkit.cakrawala.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun stringToDate(strDate: String): String {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale("id", "ID"))
    val outputFormat = SimpleDateFormat("HH:mm, dd MMM yyyy", Locale("id", "ID"))

    val date = inputFormat.parse(strDate)

    return outputFormat.format(date)
}