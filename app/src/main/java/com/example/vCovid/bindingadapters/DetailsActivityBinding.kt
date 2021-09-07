package com.example.vCovid.bindingadapters

import android.icu.text.CompactDecimalFormat
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivityBinding {

    companion object {
        @BindingAdapter("checkVisibility")
        @JvmStatic
        fun checkVisibility(recyclerView: com.todkars.shimmer.ShimmerRecyclerView, name: String) {
            if(name=="India")
                recyclerView.visibility = View.VISIBLE
            else
                recyclerView.visibility = View.GONE
        }

        @BindingAdapter("setConfirmedStates")
        @JvmStatic
        fun setConfirmedStates(textView: TextView, cases: Int) {
            val formattedNumber = CompactDecimalFormat.getInstance(Locale.getDefault(), CompactDecimalFormat.CompactStyle.SHORT).format(cases)
            textView.text = formattedNumber
        }

        @BindingAdapter("setDeathsStates")
        @JvmStatic
        fun setDeathsStates(textView: TextView, cases: Int) {
            val formattedNumber = CompactDecimalFormat.getInstance(Locale.getDefault(), CompactDecimalFormat.CompactStyle.SHORT).format(cases)
            textView.text = formattedNumber
        }

        @BindingAdapter("confirmedStates", "recoveredStates","deathsStates",requireAll = true)
        @JvmStatic
        fun setActiveStates(textView: TextView, confirmed: Int, recovered: Int, deaths: Int) {
            val formattedNumber = CompactDecimalFormat.getInstance(Locale.getDefault(), CompactDecimalFormat.CompactStyle.SHORT).format(confirmed-recovered-deaths)
            textView.text = formattedNumber
        }

        @BindingAdapter("setDate")
        @JvmStatic
        fun setDate(textView: TextView, dateStr: String) {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .parse(dateStr.replace("Z$".toRegex(), "+0000"))
            val formattedDate = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ").format(date)
            textView.text = formattedDate
        }
    }
}