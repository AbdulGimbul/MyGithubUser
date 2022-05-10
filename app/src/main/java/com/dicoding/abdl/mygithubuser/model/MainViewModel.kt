package com.dicoding.abdl.mygithubuser.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel: ViewModel() {

    val listUser = MutableLiveData<ArrayList<User>>()
    val listUserFollowing = MutableLiveData<ArrayList<UserFollowing>>()
    val listUserFollower = MutableLiveData<ArrayList<UserFollower>>()

    fun setUser(username: String){
        val listItems = ArrayList<User>()

        
        val client = AsyncHttpClient()
        val searchUrl = "https://api.github.com/search/users?q=$username"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(searchUrl, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {

                    //parsing json
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("items")

                    for (i in 0 until list.length()){
                        val user = list.getJSONObject(i)
                        val userItems = User()
                        userItems.name = user.getString("login")
                        userItems.username = user.getInt("id").toString()
                        userItems.avatar = user.getString("avatar_url")
                        userItems.location = user.getString("type")
                        listItems.add(userItems)
                    }

                    //set data ke adapteer
                    listUser.postValue(listItems)
                }catch (e: Exception){
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }


        })
    }

    fun getUser(): LiveData<ArrayList<User>>{
        return listUser
    }

    fun setFollowingUser(username: String){
        val listFollowing = ArrayList<UserFollowing>()


        val client = AsyncHttpClient()
        val searchUrl = "https://api.github.com/users/$username/following"
        client.addHeader("Authorization", "token d951d8dcf731b17fb88f759da4f886ee595060f9")
        client.addHeader("User-Agent", "request")
        client.get(searchUrl, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    //parsing json
                    val result = String(responseBody)
                    val responseArray = JSONArray(result)
                    val list = responseArray

                    for (i in 0 until list.length()){
                        val user = list.getJSONObject(i)
                        val userItems = UserFollowing()
                        userItems.name = user.getString("login")
                        userItems.username = user.getInt("id").toString()
                        userItems.avatar = user.getString("avatar_url")
                        listFollowing.add(userItems)
                    }

                    //set data ke adapteer
                    listUserFollowing.postValue(listFollowing)
                }catch (e: Exception){
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }


        })
    }

    fun getUserFollowing(): LiveData<ArrayList<UserFollowing>>{
        return listUserFollowing
    }


    fun setFollowerUser(username: String){
        val listFollower = ArrayList<UserFollower>()


        val client = AsyncHttpClient()
        val searchUrl = "https://api.github.com/users/$username/followers"
        client.addHeader("Authorization", "token d951d8dcf731b17fb88f759da4f886ee595060f9")
        client.addHeader("User-Agent", "request")
        client.get(searchUrl, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    //parsing json
                    val result = String(responseBody)
                    val responseArray = JSONArray(result)
                    val list = responseArray

                    for (i in 0 until list.length()){
                        val user = list.getJSONObject(i)
                        val userItems = UserFollower()
                        userItems.name = user.getString("login")
                        userItems.username = user.getInt("id").toString()
                        userItems.avatar = user.getString("avatar_url")
                        listFollower.add(userItems)
                    }

                    //set data ke adapteer
                    listUserFollower.postValue(listFollower)
                }catch (e: Exception){
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }


        })
    }

    fun getUserFollower(): LiveData<ArrayList<UserFollower>>{
        return listUserFollower
    }
}