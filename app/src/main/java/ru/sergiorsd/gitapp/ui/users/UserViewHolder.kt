package ru.sergiorsd.gitapp.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.sergiorsd.gitapp.R
import ru.sergiorsd.gitapp.domain.entities.UserEntity
import ru.sergiorsd.gitapp.databinding.ItemUserBinding

class UserViewHolder(
    parent: ViewGroup,
    private val onItemClickListener: (userEntity: UserEntity) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
) {
    private lateinit var userEntity: UserEntity

    private val binding = ItemUserBinding.bind(itemView).apply {
        root.setOnClickListener {
            onItemClickListener.invoke(userEntity)
        }
    }

    fun bind(userEntity: UserEntity) {
        this.userEntity = userEntity
        binding.avatarImageView.load(userEntity.avatarUrl)
        binding.loginTextView.text = userEntity.login
        binding.uidTextView.text = userEntity.id.toString()
    }
}