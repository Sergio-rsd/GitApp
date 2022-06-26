package ru.sergiorsd.gitapp.ui.users

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.sergiorsd.gitapp.domain.entities.UserEntity

class UsersAdapter(
    private val onItemClickListener: (UserEntity) -> Unit
) : RecyclerView.Adapter<UserViewHolder>() {
    private val data = mutableListOf<UserEntity>()

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int) = getIItemUser(position).id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(parent, onItemClickListener)

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getIItemUser(position))
    }

    override fun getItemCount() = data.size

    private fun getIItemUser(position: Int) = data[position]

    fun setData(users: List<UserEntity>) {
        data.clear()
        data.addAll(users)
        notifyDataSetChanged()
    }
}