package com.pthw.appbase

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs

/**
 * Created by P.T.H.W on 30/03/2023.
 */
object DateTimeAgoUtility {
    fun getTimeAgo(date: Date?, ctx: Context): String? {
        if (date == null) {
            return null
        }
        val time = date.time
        val curDate = currentDate()
        val now = curDate.time
        if (time > now || time <= 0) {
            return null
        }
        val dim = getTimeDistanceInMinutes(time)
        var timeAgo: String? = null
        timeAgo = if (dim == 0) {
            ctx.resources.getString(R.string.date_util_term_less) + " " + ctx.resources.getString(R.string.date_util_term_a) + " " + ctx.resources.getString(
                R.string.date_util_unit_minute
            )
        } else if (dim == 1) {
            return "1 " + ctx.resources.getString(R.string.date_util_unit_minute)
        } else if (dim >= 2 && dim <= 44) {
            dim.toString() + " " + ctx.resources.getString(R.string.date_util_unit_minutes)
        } else if (dim >= 45 && dim <= 89) {
            ctx.resources.getString(R.string.date_util_prefix_about) + " " + ctx.resources.getString(
                R.string.date_util_term_an
            ) + " " + ctx.resources.getString(
                R.string.date_util_unit_hour
            )
        } else if (dim >= 90 && dim <= 1439) {
            ctx.resources.getString(R.string.date_util_prefix_about) + " " + Math.round((dim / 60).toFloat()) + " " + ctx.resources.getString(
                R.string.date_util_unit_hours
            )
        } else if (dim >= 1440 && dim <= 2519) {
            "1 " + ctx.resources.getString(R.string.date_util_unit_day)
        } else if (dim >= 2520 && dim <= 43199) {
            Math.round((dim / 1440).toFloat())
                .toString() + " " + ctx.resources.getString(R.string.date_util_unit_days)
        } else if (dim >= 43200 && dim <= 86399) {
            ctx.resources.getString(R.string.date_util_prefix_about) + " " + ctx.resources.getString(
                R.string.date_util_term_a
            ) + " " + ctx.resources.getString(
                R.string.date_util_unit_month
            )
        } else if (dim >= 86400 && dim <= 525599) {
            Math.round((dim / 43200).toFloat())
                .toString() + " " + ctx.resources.getString(R.string.date_util_unit_months)
        } else if (dim >= 525600 && dim <= 655199) {
            ctx.resources.getString(R.string.date_util_prefix_about) + " " + ctx.resources.getString(
                R.string.date_util_term_a
            ) + " " + ctx.resources.getString(
                R.string.date_util_unit_year
            )
        } else if (dim >= 655200 && dim <= 914399) {
            ctx.resources.getString(R.string.date_util_prefix_over) + " " + ctx.resources.getString(
                R.string.date_util_term_a
            ) + " " + ctx.resources.getString(
                R.string.date_util_unit_year
            )
        } else if (dim >= 914400 && dim <= 1051199) {
            ctx.resources.getString(R.string.date_util_prefix_almost) + " 2 " + ctx.resources.getString(
                R.string.date_util_unit_years
            )
        } else {
            ctx.resources.getString(R.string.date_util_prefix_about) + " " + Math.round((dim / 525600).toFloat()) + " " + ctx.resources.getString(
                R.string.date_util_unit_years
            )
        }
        return timeAgo + " " + ctx.resources.getString(R.string.date_util_suffix)
    }

    private fun getTimeDistanceInMinutes(time: Long): Int {
        val timeDistance = currentDate().time - time
        return Math.round((abs(timeDistance.toDouble()) / 1000 / 60).toFloat())
    }

    private fun currentDate(): Date {
        val calendar = Calendar.getInstance()
        return calendar.time
    }

    //==========================================//
    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS
    private const val NOTIFICATION_DATE_FORMAT = "dd MMMM yyyy 'at' hh:mm a"
    private const val NOTIFICATION_WEEK_DATE_FORMAT = "EEEE 'at' hh:mm a"
    private const val NOTIFICATION_YESTERDAY_DATE_FORMAT = "'Yesterday,' hh:mm a"
    fun getTimeAgoStyle2(date: Date?, ctx: Context?): String? {
        if (date == null) {
            return null
        }
        val time = date.time
        val curDate = currentDate()
        val now = curDate.time
        if (time > now || time <= 0) {
            return null
        }

        // TODO: localize
        val diff = now - time
        return if (diff < MINUTE_MILLIS) {
            "just now"
        } else if (diff < 2 * MINUTE_MILLIS) {
            "a minute ago"
        } else if (diff < 50 * MINUTE_MILLIS) {
            (diff / MINUTE_MILLIS).toString() + " minutes ago"
        } else if (diff < 90 * MINUTE_MILLIS) {
            "an hour ago"
        } else if (diff < 24 * HOUR_MILLIS) {
            (diff / HOUR_MILLIS).toString() + " hours ago"
        } else if (diff < 48 * HOUR_MILLIS) {
            dateFormatter(
                date,
                NOTIFICATION_YESTERDAY_DATE_FORMAT
            )
        } else if (diff < 7 * DAY_MILLIS) {
            dateFormatter(date, NOTIFICATION_WEEK_DATE_FORMAT)
        } else {
            dateFormatter(date, NOTIFICATION_DATE_FORMAT)
        }
    }

    fun dateFormatter(date: Date?, format: String?): String {
        val formatter = SimpleDateFormat(format, Locale.ENGLISH)
        return formatter.format(date)
    }
}
