package com.dicoding.abdl.mygithubuser.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserFollowing (
    var username: String? = "",
    var name: String? = "",
    var avatar: String? = ""
): Parcelable