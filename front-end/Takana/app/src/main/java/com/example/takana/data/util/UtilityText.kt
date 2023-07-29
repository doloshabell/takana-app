package com.example.takana.data.util

import com.example.takana.data.Constants
import java.text.NumberFormat
import java.util.*

fun Long?.toRupiah(isNotHypen: Boolean = false): String? {
    if (this == null || this == 0L || this < 0) {
        if (isNotHypen) return Constants.RP_0
        return Constants.HYPHEN
    }
    var indCurrency = this.toString()
    if (this >= 1000) {
        val convertedNumber = NumberFormat.getNumberInstance(Locale.US).format(this)
        indCurrency = convertedNumber.replace(
            Constants.COMMA_DELIMITER, Constants.DOT
        )
    }
    return "Rp $indCurrency"
}

fun Double?.isValid(
): Boolean {
    return this != null && this != 0.0 && this > 0
}
