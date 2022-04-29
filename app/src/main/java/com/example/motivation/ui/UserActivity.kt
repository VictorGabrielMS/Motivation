package com.example.motivation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.motivation.core.Constants
import com.example.motivation.R
import com.example.motivation.infra.SecurityPreferences
import com.example.motivation.databinding.ActivityUserBinding


class UserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityUserBinding

    private lateinit var storage: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        storage = SecurityPreferences(this)

        verifyUserAuth()
        listeners()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_save -> handleSave()
        }
    }

    private fun verifyUserAuth() {
        val name = storage.getString(Constants.KEY.USER_NAME)
        if (name.isNotEmpty()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun listeners() {
        binding.buttonSave.setOnClickListener(this)
    }

    private fun handleSave() {
        val name = binding.editName.text.toString();
        if (name.isNotEmpty()) {
            storage.storeSting(Constants.KEY.USER_NAME, name)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast
                .makeText(
                    this,
                    R.string.validation_mandatory_name,
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    }
}