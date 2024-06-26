package com.example.vCovid.bindingadapters

import android.icu.text.CompactDecimalFormat
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.vCovid.models.summary.Country
import com.example.vCovid.ui.fragments.country.CountryFragmentDirections
import java.lang.Exception
import java.util.*

// To use binding adapters for country row layout file 
class CountryRowBinding {

    companion object {
        @BindingAdapter("onCountryClickListener")
        @JvmStatic
        fun onCountryClickListener(countryRowLayout: ConstraintLayout, result: Country) {
            countryRowLayout.setOnClickListener {
                try {
                    val action =
                        CountryFragmentDirections.actionCountryFragmentToDetailsActivity(result)
                    countryRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onCountryClickListener", e.toString())
                }
            }
        }

        @BindingAdapter("setConfirmed")
        @JvmStatic
        fun setConfirmed(textView: TextView, cases: Int) {
            val formattedNumber = CompactDecimalFormat.getInstance(Locale.getDefault(), CompactDecimalFormat.CompactStyle.SHORT).format(cases)
            textView.text = formattedNumber
        }

        @BindingAdapter("setDeaths")
        @JvmStatic
        fun setDeaths(textView: TextView, cases: Int) {
            val formattedNumber = CompactDecimalFormat.getInstance(Locale.getDefault(), CompactDecimalFormat.CompactStyle.SHORT).format(cases)
            textView.text = formattedNumber
        }

        @BindingAdapter("confirmed", "recovered","deaths",requireAll = true)
        @JvmStatic
        fun setActive(textView: TextView, confirmed: Int, recovered: Int, deaths: Int) {
            val formattedNumber = CompactDecimalFormat.getInstance(Locale.US, CompactDecimalFormat.CompactStyle.SHORT).format(confirmed-recovered-deaths)
            textView.text = formattedNumber
        }

        @BindingAdapter("setDischarged")
        @JvmStatic
        fun setDischarged(textView: TextView, cases: Int) {
            val formattedNumber = CompactDecimalFormat.getInstance(Locale.US, CompactDecimalFormat.CompactStyle.SHORT).format(cases)
            textView.text = formattedNumber
        }
    }

}
