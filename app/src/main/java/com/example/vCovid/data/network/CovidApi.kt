package com.example.vCovid.data.network

import com.example.vCovid.models.country_details.CountryDetailsData
import com.example.vCovid.models.summary.SummaryData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidApi {

    @GET("/summary")
    suspend fun getSummary(
    ): Response<SummaryData>

    @GET("/total/dayone/country/{CountryName}")
    suspend fun getDayOneIndividualData(@Path("CountryName")  CountryName:String
    ): Response<CountryDetailsData>

}