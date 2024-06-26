package com.example.vCovid.ui.fragments.country

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vCovid.viewmodels.CountryViewModel
import com.example.vCovid.R
import com.example.vCovid.adapters.CountriesAdapter
import com.example.vCovid.databinding.FragmentCountryBinding
import com.example.vCovid.models.summary.Country
import com.example.vCovid.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*
import kotlin.collections.ArrayList
import com.google.android.material.chip.Chip


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CountryFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding
    private lateinit var countryViewModel: CountryViewModel
    private val mAdapterCountries by lazy {CountriesAdapter()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        countryViewModel = ViewModelProvider(requireActivity()).get(CountryViewModel::class.java)
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = this
        binding?.mainViewModel = countryViewModel
        setHasOptionsMenu(true)
        setupRecyclerViewForCountries()
        requestApiDataForCountries()
        observeAndUpdateView()
        binding?.chipGroup?.setOnCheckedChangeListener { group, checkedId ->
            handleChipRequest(checkedId)
        }
        return binding?.root
    }

    private fun handleChipRequest(checkedId:Int) {
        if(binding == null) return
        val checkedChipId = binding?.chipGroup?.findViewById<Chip>(checkedId)
        when(checkedChipId?.text.toString()) {
            "Confirmed" -> {
                if(binding?.chip1Img?.visibility == View.GONE) {
                    binding?.chip1Img?.visibility = View.VISIBLE
                    binding?.chip2Img?.visibility = View.GONE
                    binding?.chip1?.visibility = View.VISIBLE
                    binding?.chip2?.visibility = View.GONE
                    sortDataConfirmed(false)
                }
                else {
                    binding?.chip2Img?.visibility = View.VISIBLE
                    binding?.chip1Img?.visibility = View.GONE
                    binding?.chip2?.visibility = View.VISIBLE
                    binding?.chip1?.visibility = View.GONE
                    sortDataConfirmed(true)
                }
            }
            "Deaths" -> {
                if(binding?.chip3Img?.visibility == View.GONE) {
                    binding?.chip3Img?.visibility = View.VISIBLE
                    binding?.chip4Img?.visibility = View.GONE
                    binding?.chip3?.visibility = View.VISIBLE
                    binding?.chip4?.visibility = View.GONE
                    sortDataDeaths(false)
                }
                else {
                    binding?.chip4Img?.visibility = View.VISIBLE
                    binding?.chip3Img?.visibility = View.GONE
                    binding?.chip4?.visibility = View.VISIBLE
                    binding?.chip3?.visibility = View.GONE
                    sortDataDeaths(true)
                }
            }
            "Active" -> {
                if(binding?.chip5Img?.visibility == View.GONE) {
                    binding?.chip5Img?.visibility = View.VISIBLE
                    binding?.chip6Img?.visibility = View.GONE
                    binding?.chip5?.visibility = View.VISIBLE
                    binding?.chip6?.visibility = View.GONE
                    sortDataActive(false)
                }
                else {
                    binding?.chip6Img?.visibility = View.VISIBLE
                    binding?.chip5Img?.visibility = View.GONE
                    binding?.chip6?.visibility = View.VISIBLE
                    binding?.chip5?.visibility = View.GONE
                    sortDataActive(true)
                }
            }
        }
    }

    private fun sortDataConfirmed(flag:Boolean){
        countryViewModel.sortSummaryResponse(flag,"Confirmed")
    }

    private fun sortDataDeaths(flag:Boolean){
        countryViewModel.sortSummaryResponse(flag,"Deaths")
    }

    private fun sortDataActive(flag:Boolean){
        countryViewModel.sortSummaryResponse(flag,"Active")
    }

    private fun setupRecyclerViewForCountries() {
        binding?.countriesRecyclerView?.adapter = mAdapterCountries
        binding?.countriesRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.country_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        filterResult(query)
        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        filterResult(query.toLowerCase(Locale.getDefault()))
        return true
    }

    private fun filterResult(query: String) {
        var filteredlist = countryViewModel.filterCountriesList(query)
        mAdapterCountries.setData(filteredlist)
    }

    private fun observeAndUpdateView() {
        countryViewModel.summaryResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let {
                        mAdapterCountries.setData(it.countries) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun requestApiDataForCountries() {
        countryViewModel.getSummaryCountries()
    }

    private fun showShimmerEffect() {
        binding?.countriesRecyclerView?.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding?.countriesRecyclerView?.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}