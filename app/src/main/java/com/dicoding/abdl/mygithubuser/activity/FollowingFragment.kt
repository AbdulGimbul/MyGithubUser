package com.dicoding.abdl.mygithubuser.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.abdl.mygithubuser.R
import com.dicoding.abdl.mygithubuser.adapter.FollowingListUserAdapter
import com.dicoding.abdl.mygithubuser.model.MainViewModel
import kotlinx.android.synthetic.main.fragment_following.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class FollowingFragment : Fragment() {
    private lateinit var rvFollowing: FollowingListUserAdapter
    private var mainViewModel = MainViewModel()

    companion object {
        private var ARG_USERNAME = "username"

        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFollowing = FollowingListUserAdapter()
        rvFollowing.notifyDataSetChanged()

        mainViewModel = ViewModelProvider(
            activity!!,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        rv_following.layoutManager = LinearLayoutManager(activity)
        rv_following.adapter = rvFollowing

        val username = arguments?.getString(ARG_USERNAME)
        if (username != null) {
            mainViewModel.setFollowingUser(username)
        }

        mainViewModel.getUserFollowing().observe(activity!!, Observer { userItems ->
            if (userItems != null) {
                rvFollowing.setData(userItems)
            }
        })
    }
}


