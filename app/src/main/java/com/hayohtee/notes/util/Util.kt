package com.hayohtee.notes.util

import android.icu.text.DateFormat
import android.text.format.DateUtils
import java.util.Date

object Util {
    fun dateToRelativeTimeString(date: Date): String {
        return DateUtils.getRelativeTimeSpanString(date.time).toString()
    }

    fun dateToString(date: Date): String {
        return DateFormat.getPatternInstance(DateFormat.YEAR_MONTH_DAY).format(date)
    }
}