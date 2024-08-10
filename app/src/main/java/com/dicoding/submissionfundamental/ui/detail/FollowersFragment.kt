package com.dicoding.submissionfundamental.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionfundamental.data.response.FollowersResponseItem
import com.dicoding.submissionfundamental.data.retrofit.ApiConfig
import com.dicoding.submissionfundamental.databinding.FragmentFollowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowersFragment : Fragment() {
    private var position: Int = 0
    private var username: String? = null
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(SectionsPagerAdapter.ARG_POSITION)
            username = it.getString(SectionsPagerAdapter.ARG_USERNAME)
        }

        val client = ApiConfig.getApiService().getFollowersGithub(username ?: "")
        client.enqueue(object : Callback<List<FollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersResponseItem>>,
                response: Response<List<FollowersResponseItem>>
            ) {
                if (response.isSuccessful) {
                    binding.rvGithub.visibility = View.VISIBLE
                    binding.pgGithub.visibility = View.GONE
                    val listFollowers = response.body()
                    Log.d("TAG", "onResponse: ${listFollowers?.size}")
                    if (listFollowers!=null) {
                        binding.rvGithub.layoutManager = LinearLayoutManager(context)
                        binding.rvGithub.adapter = FollowersAdapter(listFollowers)
                    }
                } else {
                    Toast.makeText(context, "Gagal mendapatkan followers", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                Toast.makeText(context, "Error ketika mendapatkan followers", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }
}