package ru.sergiorsd.gitapp.ui.profile

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import ru.sergiorsd.gitapp.R
import ru.sergiorsd.gitapp.databinding.ItemUserDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ItemUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ItemUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent :Intent = intent
        binding.idDetails.text = intent.getStringExtra("id")
        binding.loginDetails.text = intent.getStringExtra("login")
        binding.imageAvatarDetails.load(intent.getStringExtra("url"))

    }
}