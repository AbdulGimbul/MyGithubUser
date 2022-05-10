package com.dicoding.abdl.mygithubuser.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.dicoding.abdl.mygithubuser"
    const val SCHEME = "content"

    internal class UserColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "github_user"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val NAME = "name"
            const val LOCATION = "location"
            const val AVATAR = "avatar"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}