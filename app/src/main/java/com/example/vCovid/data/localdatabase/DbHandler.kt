package com.example.vCovid.data.localdatabase


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.vCovid.data.localdatabase.favouriteCountry.FavouriteParams

class DbHandler constructor(context: Context) :
    SQLiteOpenHelper(context, DbParams.DATABASE_NAME, null, DbParams.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery = FavouriteParams.CREATE_FAVOURITE_TABLE
        db?.execSQL(createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        if(db == null) return
        db.execSQL("DROP TABLE IF EXISTS " + FavouriteParams.TABLE_FAVOURITES)
        onCreate(db)
    }

}
