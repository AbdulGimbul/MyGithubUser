package com.dicoding.abdl.mygithubuser.model


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class UserFavorite (
    var username: String? = "",
    var name: String? = "",
    var location: String? = "",
    var company: String? = "",
    var avatar: String? = ""
): Parcelable