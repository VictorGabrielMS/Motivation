package com.example.motivation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.motivation.core.Constants
import com.example.motivation.R
import com.example.motivation.data.Mock
import com.example.motivation.infra.SecurityPreferences
import com.example.motivation.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var storage: SecurityPreferences

    private var filter: Int = Constants.PHRASE_CATEGORY.ALL

    private val mock: Mock = Mock()

    private val iconsIds = listOf(R.id.image_all, R.id.image_happy, R.id.image_sun)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        storage = SecurityPreferences(this)

        listeners()

        handleRefreshPhrase()
        handleFilter(iconsIds[0])
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_new_phrase -> {
                handleRefreshPhrase()
            }
            R.id.text_welcome -> {
                handleNavigate()
            }
            in iconsIds -> {
                handleFilter(view.id)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        handleWelcomeMessage()
    }

    private fun listeners() {
        binding.buttonNewPhrase.setOnClickListener(this)
        binding.imageAll.setOnClickListener(this)
        binding.imageHappy.setOnClickListener(this)
        binding.imageSun.setOnClickListener(this)
        binding.textWelcome.setOnClickListener(this)
    }

    private fun handleRefreshPhrase() {
        binding.textRandomMessage.text = Mock().getPhrase(filter, Locale.getDefault().language)
    }

    private fun handleWelcomeMessage() {
        val name = storage.getString(Constants.KEY.USER_NAME)
        val hello = getString(R.string.hello)
        binding.textWelcome.text = "$hello, $name!"
    }

    private fun handleNavigate() {
        storage.storeSting(Constants.KEY.USER_NAME, "")
        startActivity(Intent(this, UserActivity::class.java))
        finish()
    }

    private fun handleFilter(id: Int) {
        val ta = this.theme.obtainStyledAttributes(R.styleable.ViewStyle)

        iconsIds.forEach {
            findViewById<ImageView>(it).setColorFilter(
                ta.getColor(R.styleable.ViewStyle_primaryVariant, android.R.attr.defaultValue)
            )
        }

        when (id) {
            iconsIds[0] -> {
                filter = Constants.PHRASE_CATEGORY.ALL
                binding.imageAll.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            }
            iconsIds[1] -> {
                filter = Constants.PHRASE_CATEGORY.HAPPY
                binding.imageHappy.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            }
            iconsIds[2] -> {
                filter = Constants.PHRASE_CATEGORY.SUN
                binding.imageSun.setColorFilter(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            }

        }
    }


}