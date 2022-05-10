package com.dicoding.abdl.userfavoritegithub.helper

import android.database.Cursor
import android.util.Log
import com.dicoding.abdl.userfavoritegithub.db.DatabaseContract
import com.dicoding.abdl.userfavoritegithub.model.User

object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<User>{
        val listUser = ArrayList <User>()
        cursor?.apply {
            while (moveToNext()){
                val user = User()
                user.username = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME))
                user.avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR))
                user.name = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME))
                user.location = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION))
                listUser.add(user)
                Log.d("Mapping helper dikirim", user.toString())
            }

        }
        return listUser
    }
}