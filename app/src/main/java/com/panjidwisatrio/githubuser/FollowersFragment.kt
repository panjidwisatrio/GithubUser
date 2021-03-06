package com.panjidwisatrio.githubuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.panjidwisatrio.githubuser.databinding.FragmentFollowersBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class FollowersFragment : Fragment() {

    private var _binding : FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private var listFollowers : ArrayList<UserData> = ArrayList()
    private lateinit var adapterFollowers: FollowersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterFollowers = FollowersAdapter(listFollowers)
        listFollowers.clear()
        arguments?.getInt("section_number", 0)
        val dataUser = activity?.intent?.getParcelableExtra<UserData>(EXTRA_DATA)
        getUserFollowers(dataUser?.username.toString())
    }

    private fun getUserFollowers(id: String) {
        val progressBarFollowers = binding.progressBarFollowers
        progressBarFollowers.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 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")
        val url = "https://api.github.com/users/$id/followers"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                progressBarFollowers.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username : String =jsonObject.getString("login")
                        getUserDetail(username)
                    }
                } catch (e : Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                progressBarFollowers.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun getUserDetail(id: String) {
        val progressBarFollowers = binding.progressBarFollowers
        progressBarFollowers.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token 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")
        val url = "https://api.github.com/users/$id"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                progressBarFollowers.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val username: String = jsonObject.getString("login").toString()
                    val name: String = jsonObject.getString("name").toString()
                    val avatar: String = jsonObject.getString("avatar_url").toString()
                    val company: String = jsonObject.getString("company").toString()
                    val location: String = jsonObject.getString("location").toString()
                    val repository: String? = jsonObject.getString("public_repos")
                    val followers: String? = jsonObject.getString("followers")
                    val following: String? = jsonObject.getString("following")

                    listFollowers.add(
                        UserData(
                            username,
                            name,
                            avatar,
                            company,
                            location,
                            repository,
                            followers,
                            following
                        )
                    )
                    showRecyclerViewList()
                } catch (e : Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                progressBarFollowers.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showRecyclerViewList() {
        with(binding.rvListUser) {
            layoutManager = LinearLayoutManager(activity)
            adapter = adapterFollowers
        }
    }

    companion object {
        private val TAG = FollowersFragment::class.java.simpleName
        const val EXTRA_DATA = "extra_data"
    }
}