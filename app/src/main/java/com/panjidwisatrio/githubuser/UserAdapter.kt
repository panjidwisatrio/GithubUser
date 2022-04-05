package com.panjidwisatrio.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panjidwisatrio.githubuser.databinding.ItemRowUserBinding
import java.util.*
import kotlin.collections.ArrayList

var filterData = ArrayList<UserData>()
class UserAdapter(private val listData : ArrayList<UserData>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>(), Filterable {

    init {
        filterData = listData
    }

    private lateinit var onItemClickCallback : OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClickCallback(data : UserData)
    }

    inner class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user : UserData)= with(binding) {
            Glide.with(this.root)
                .load(user.avatar)
                .into(avatars)

            username.text = user.username
            company.text = user.company
            follower.text = user.followers
            following.text = user.following
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRowUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        bind(listData[position])
        itemView.setOnClickListener {onItemClickCallback.onItemClickCallback(listData[adapterPosition])}
    }

    override fun getItemCount(): Int = filterData.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charSearch = constraint.toString()
                filterData = if (charSearch.isEmpty()) {
                    listData
                } else {
                    val result = ArrayList<UserData>()
                    for (row in filterData) {
                        if ((row.username.toString().lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT)))
                        ) {
                            result.add(
                                UserData(
                                    row.username,
                                    row.name,
                                    row.avatar,
                                    row.company,
                                    row.location,
                                    row.repository,
                                    row.followers,
                                    row.following
                                )
                            )
                        }
                    }
                    result
                }
                val filterResult = FilterResults()
                filterResult.values = filterData
                return filterResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence, result: FilterResults) {
                filterData = result.values as ArrayList<UserData>
            }

        }
    }
}