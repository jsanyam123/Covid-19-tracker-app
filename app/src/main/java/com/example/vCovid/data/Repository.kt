package com.example.vCovid.data

import com.example.vCovid.data.localdatabase.favouriteCountry.FavouriteCountryHandler
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    favouriteCountryHandler: FavouriteCountryHandler
) {
    val remote = remoteDataSource
    val dbHandler = favouriteCountryHandler
}