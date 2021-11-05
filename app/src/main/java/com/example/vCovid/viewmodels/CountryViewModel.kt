package com.example.vCovid.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.vCovid.models.FavouriteCountryModel
import com.example.vCovid.data.Repository
import com.example.vCovid.models.summary.Country
import com.example.vCovid.models.summary.SummaryData
import com.example.vCovid.util.NetworkResult
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class CountryViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {
    var favouriteCountriesResponse :MutableLiveData<NetworkResult<List<FavouriteCountryModel>>> = MutableLiveData()
    var favouriteCountries : ArrayList<FavouriteCountryModel>? = null
    var summaryResponse : MutableLiveData<NetworkResult<SummaryData>> = MutableLiveData()

    fun sortSummaryResponse(flag:Boolean,criteria:String){
        var countryList = summaryResponse.value?.data?.countries
        var dummy : List<Country>?
        when (criteria) {
            "Confirmed" -> {
                if(flag){
                    dummy = countryList?.sortedWith(compareBy{it.totalConfirmed})
                    summaryResponse.value = NetworkResult.Success(dummy?.let { SummaryData(it,"","","") })
                }
                else{
                    dummy = countryList?.sortedWith(compareBy{it.totalConfirmed})?.reversed()
                    summaryResponse.value = NetworkResult.Success(dummy?.let { SummaryData(it,"","","") })
                }
            }
            "Deaths" -> {
                if(flag){
                    dummy = countryList?.sortedWith(compareBy{it.totalDeaths})
                    summaryResponse.value = NetworkResult.Success(dummy?.let { SummaryData(it,"","","") })
                }
                else{
                    dummy = countryList?.sortedWith(compareBy{it.totalDeaths})?.reversed()
                    summaryResponse.value = NetworkResult.Success(dummy?.let { SummaryData(it,"","","") })
                }
            }
            "Active" -> {
                if(flag){
                    dummy = countryList?.sortedWith(compareBy{it.totalConfirmed-it.totalDeaths-it.totalRecovered})
                    summaryResponse.value = NetworkResult.Success(dummy?.let { SummaryData(it,"","","") })
                }
                else{
                    dummy = countryList?.sortedWith(compareBy{it.totalConfirmed-it.totalDeaths-it.totalRecovered})?.reversed()
                    summaryResponse.value = NetworkResult.Success(dummy?.let { SummaryData(it,"","","") })
                }
            }


        }
    }

    fun filterCountriesList(query:String):ArrayList<Country>
    {
        var countryList = summaryResponse.value?.data?.countries
        var filteredList : ArrayList<Country> = ArrayList()
        countryList?.forEach {
            if(it.country.toLowerCase(Locale.getDefault()).contains(query)) {
                filteredList.add(it)
            }
        }
        return filteredList
    }

    fun fetchFavoriteCountries() {
        favouriteCountries = repository.dbHandler.fetchFavCountries()
        favouriteCountriesResponse.value = NetworkResult.Success(favouriteCountries)
    }

    fun filterFavCountries(query:String) {
        var countriesList : ArrayList<FavouriteCountryModel> = arrayListOf()
        favouriteCountries?.forEach {
            if(it.name.toLowerCase(Locale.getDefault()).contains(query)) {
                countriesList.add(it)
            }
        }
        favouriteCountriesResponse.value = NetworkResult.Success(countriesList)
    }

    fun insertFavCountry(name:String, countryDetails:Country) {
        var gson = Gson()
        var details = gson.toJson(countryDetails)
        repository.dbHandler.addFavouriteCountry(FavouriteCountryModel(0, name, details))
    }

    fun deleteFavCountry(id:Int) {
        repository.dbHandler.deleteFavouriteCountry(id)
    }

    fun getSummaryCountries() = viewModelScope.launch {
        getSummaryCountriesSafeCall()
    }

    private suspend fun getSummaryCountriesSafeCall() {
        summaryResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getSummary()
                summaryResponse.value = handleSummaryResponse(response)
            } catch (e: Exception) {
                summaryResponse.value = NetworkResult.Error("Countries not found.")
            }
        } else {
            summaryResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleSummaryResponse(response: Response<SummaryData>): NetworkResult<SummaryData>? {
        return when {
            response.isSuccessful -> {
                val summary = response.body()
                NetworkResult.Success(summary)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}