package com.example.motivation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.motivation.infra.MotivationConstants
import com.example.motivation.R
import com.example.motivation.infra.SecurityPreferences
import com.example.motivation.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSupportActionBar()

        binding.saveButton.setOnClickListener(this)

        verifyUsername()

    }

    override fun onClick(view: View) {
        if (view.id == R.id.saveButton) {
           handleSave()
        }
    }

    private fun handleSave() {
        if (isValid()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun verifyUsername() {
        val userAlreadyLogged = SecurityPreferences(this).getString(MotivationConstants.KEY.USER_NAME)
        if (userAlreadyLogged != "") {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun isValid(): Boolean {
        val name = binding.editYourName.text.toString()
        return if (name != "") {
            SecurityPreferences(this).storeString(MotivationConstants.KEY.USER_NAME, name)
            true
        } else {
            Toast.makeText(this, R.string.validation_mandatory_name, Toast.LENGTH_LONG).show()
            false
        }
    }

    private fun hideSupportActionBar() {
        supportActionBar?.hide()
    }
}