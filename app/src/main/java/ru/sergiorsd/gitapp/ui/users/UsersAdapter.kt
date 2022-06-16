package ru.sergiorsd.gitapp.ui.users

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.sergiorsd.gitapp.domain.entities.UserEntityDTO

class UsersAdapter : RecyclerView.Adapter<UserViewHolder>() {
    private val data = mutableListOf<UserEntityDTO>()

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int) = getIItemUser(position).id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(parent)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getIItemUser(position))
    }

    override fun getItemCount() = data.size

    private fun getIItemUser(position: Int) = data[position]

    fun setData(users: List<UserEntityDTO>) {
        data.clear()
        data.addAll(users)
        notifyDataSetChanged()
    }
}