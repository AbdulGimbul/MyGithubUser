package com.dicoding.abdl.mygithubuser.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dicoding.abdl.mygithubuser.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.dicoding.abdl.mygithubuser.db.DatabaseContract.UserColumns.Companion.LOCATION
import com.dicoding.abdl.mygithubuser.db.DatabaseContract.UserColumns.Companion.NAME
import com.dicoding.abdl.mygithubuser.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.dicoding.abdl.mygithubuser.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.dicoding.abdl.mygithubuser.db.DatabaseContract.UserColumns.Companion._ID

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "dbgithubuser"
        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME" +
                "($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${USERNAME} TEXT NOT NULL UNIQUE," +
                " ${NAME} TEXT NOT NULL," +
                " ${LOCATION} TEXT NOT NULL," +
                " ${AVATAR} INTEGER NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}