package com.ceylonapz.weatherlive.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ceylonapz.weatherlive.databinding.ActivitySignupBinding
import com.ceylonapz.weatherlive.ui.MainActivity
import com.infinity.movieapp.extensions.navigateActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            navigateActivity(MainActivity::class.java,null,null)
        }
    }
}