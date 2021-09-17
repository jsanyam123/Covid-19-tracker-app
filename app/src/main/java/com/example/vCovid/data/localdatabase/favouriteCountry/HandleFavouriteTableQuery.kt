package com.example.vCovid.data.localdatabase.favouriteCountry

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.example.vCovid.models.FavouriteCountryModel

class HandleFavouriteTableQuery {

    fun addFavouriteCountry(db:SQLiteDatabase,favouriteCountry: FavouriteCountryModel) :Long {
        val contentValues = ContentValues()
        contentValues.put(FavouriteParams.FAVOURITE_TABLE_KEY_NAME, favouriteCountry.name)
        contentValues.put(FavouriteParams.FAVOURITE_TABLE_KEY_DETAILS, favouriteCountry.details)
        val success = db.insert(FavouriteParams.TABLE_FAVOURITES, null, contentValues)
        return success
    }

    fun fetchFavCountries(db: SQLiteDatabase): ArrayList<FavouriteCountryModel> {
        val favouriteCountryList: ArrayList<FavouriteCountryModel> = ArrayList()
        var cursor: Cursor
        try {
            cursor = db.query(FavouriteParams.TABLE_FAVOURITES, null,null,null,null,null,null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }
        var id: Int
        var name: String
        var details: String
        if (cursor.moveToFirst()) do {
            cursor.getInt(cursor.getColumnIndex(FavouriteParams.FAVOURITE_TABLE_KEY_ID)).also { id = it }
            cursor.getString(cursor.getColumnIndex(FavouriteParams.FAVOURITE_TABLE_KEY_NAME)).also { name = it }
            cursor.getString(cursor.getColumnIndex(FavouriteParams.FAVOURITE_TABLE_KEY_DETAILS))
                .also { details = it }
            val emp = FavouriteCountryModel(id = id, name = name, details = details)
            favouriteCountryList.add(emp)
        } while (cursor.moveToNext())
        cursor.close()
        return favouriteCountryList
    }

    fun deleteFavouriteCountry(db:SQLiteDatabase,favouriteCountryId: Int): Int{
        val contentValues = ContentValues()
        contentValues.put(FavouriteParams.FAVOURITE_TABLE_KEY_ID, favouriteCountryId)
        val success = db.delete(FavouriteParams.TABLE_FAVOURITES, (FavouriteParams.FAVOURITE_TABLE_KEY_ID + "=$favouriteCountryId"), null)
        return success
    }
}