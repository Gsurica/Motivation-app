package com.example.motivation.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.motivation.infra.MotivationConstants
import com.example.motivation.R
import com.example.motivation.data.Mock
import com.example.motivation.infra.SecurityPreferences
import com.example.motivation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var categoryId = MotivationConstants.CATEGORY.ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        overrideUsernameOnScreen()
        hideSupportActionBar()

        handleFilter(R.id.image_all)

        setFirstPhrase()

        binding.buttonNewPhrase.setOnClickListener(this)
        binding.imageAll.setOnClickListener(this)
        binding.imageSunny.setOnClickListener(this)
        binding.imageHappy.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if(view.id == R.id.buttonNewPhrase) {
            handleNewPhrase()
        } else if (view.id in listOf(R.id.image_all, R.id.image_happy, R.id.image_sunny)) {
            handleFilter(view.id)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun overrideUsernameOnScreen() {
        if (getStorageName() != "") {
            binding.textUserName.text = "Olá, ${getStorageName()}!"
        } else {
            binding.textUserName.text = "Olá, Pessoinha!"
        }
    }

    private fun handleNewPhrase() {
        binding.textMotivation.text = Mock().getPhrase(categoryId)
    }

    private fun handleFilter(id: Int) {

        binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))
        binding.imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))
        binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))

        when (id) {
            R.id.image_all -> {
                binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.white))
                categoryId = MotivationConstants.CATEGORY.ALL
            }
            R.id.image_happy -> {
                binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.white))
                categoryId = MotivationConstants.CATEGORY.HAPPY
            }
            R.id.image_sunny -> {
                binding.imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.white))
                categoryId = MotivationConstants.CATEGORY.SUNNY
            }
        }
    }

    private fun setFirstPhrase() {
        binding.textMotivation.text = Mock().getPhrase(categoryId)
    }

    private fun getStorageName(): String {
        return SecurityPreferences(this).getString(MotivationConstants.KEY.USER_NAME)
    }

    private fun hideSupportActionBar() {
        supportActionBar?.hide()
    }
}