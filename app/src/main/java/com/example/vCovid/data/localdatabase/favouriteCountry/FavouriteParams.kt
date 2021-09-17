package com.example.vCovid.data.localdatabase.favouriteCountry

class FavouriteParams {

    companion object {

        const val TABLE_FAVOURITES = "FavoriteCountry"
        const val FAVOURITE_TABLE_KEY_ID = "_id"
        const val FAVOURITE_TABLE_KEY_NAME = "name"
        const val FAVOURITE_TABLE_KEY_DETAILS = "details"

        const val CREATE_FAVOURITE_TABLE = ("CREATE TABLE " + TABLE_FAVOURITES + "("
                + FAVOURITE_TABLE_KEY_ID + " INTEGER PRIMARY KEY," + FAVOURITE_TABLE_KEY_NAME + " TEXT,"
                + FAVOURITE_TABLE_KEY_DETAILS + " TEXT" + ")")

    }
}