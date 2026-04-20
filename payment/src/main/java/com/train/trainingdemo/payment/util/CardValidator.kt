package com.train.trainingdemo.payment.util

object CardValidator {
    fun isValidLuhn(number: String): Boolean {
        var sum = 0
        var alternate = false
        for (i in number.length - 1 downTo 0) {
            var n = number[i] - '0'
            if (alternate) {
                n *= 2
                if (n > 9) n -= 9
            }
            sum += n
            alternate = !alternate
        }
        return sum % 10 == 0
    }

    fun isValidExpiry(expiry: String): Boolean {
        if (!expiry.matches(Regex("^(0[1-9]|1[0-2])/?([0-9]{2})$"))) return false
        val parts = expiry.split("/")
        if (parts.size != 2) return false
        val month = parts[0].toInt()
        val year = 2000 + parts[1].toInt()
        
        val now = java.util.Calendar.getInstance()
        val currentYear = now.get(java.util.Calendar.YEAR)
        val currentMonth = now.get(java.util.Calendar.MONTH) + 1
        
        return year > currentYear || (year == currentYear && month >= currentMonth)
    }

    fun isValidCvv(cvv: String): Boolean {
        return cvv.length in 3..4 && cvv.all { it.isDigit() }
    }
}
