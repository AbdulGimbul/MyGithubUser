package com.dicoding.abdl.mygithubuser.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDetail (
    var username: String? = "",
    var name: String? = "",
    var location: String? = "",
    var avatar: String? = ""
): Parcelable