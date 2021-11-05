package com.example.vCovid.data.localdatabase.favouriteCountry

import android.content.Context
import com.example.vCovid.data.localdatabase.DbHandler
import com.example.vCovid.models.FavouriteCountryModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FavouriteCountryHandler @Inject constructor(@ApplicationContext context: Context) {

    private val dbHelper = DbHandler(context)

    fun addFavouriteCountry(favouriteCountry: FavouriteCountryModel): Long {
        val db = dbHelper.writableDatabase
        val success = HandleFavouriteTableQuery().addFavouriteCountry(db, favouriteCountry)
        db.close()
        return success
    }

    fun fetchFavCountries(): ArrayList<FavouriteCountryModel> {
        val db = dbHelper.readableDatabase
        val resultList = HandleFavouriteTableQuery().fetchFavCountries(db)
        db.close()
        return resultList
    }

    fun deleteFavouriteCountry(favouriteCountryId: Int): Int {
        val db = dbHelper.writableDatabase
        val result = HandleFavouriteTableQuery().deleteFavouriteCountry(db, favouriteCountryId)
        db.close()
        return result
    }
}