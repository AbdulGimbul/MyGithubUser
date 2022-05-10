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
import com.dicoding.abdl.mygithubuser.adapter.FollowerListUserAdapter
import com.dicoding.abdl.mygithubuser.model.MainViewModel
import kotlinx.android.synthetic.main.fragment_follower.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowerFragment : Fragment() {
    private lateinit var rvFollower: FollowerListUserAdapter
    private var mainViewModel = MainViewModel()

    companion object {
        private val ARG_USERNAME = "username"

        fun newInstance(username: String): FollowerFragment {
            val fragment = FollowerFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rv_follower.layoutManager = LinearLayoutManager(activity)
        rvFollower = FollowerListUserAdapter()
        rv_follower.adapter = rvFollower
        rvFollower.notifyDataSetChanged()

        mainViewModel = ViewModelProvider(
            activity!!,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        val username = arguments?.getString(ARG_USERNAME)
        if (username != null) {
            mainViewModel.setFollowerUser(username)
        }

        mainViewModel.getUserFollower().observe(activity!!, Observer { userItems ->
            if (userItems != null) {
                rvFollower.setData(userItems)
            }
        })
    }
}