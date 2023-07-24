package com.example.takana.presentation.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.takana.MainActivity
import com.example.takana.R
import com.example.takana.databinding.ActivityLoginBinding
import com.example.takana.presentation.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupContent()
    }

    private fun setupContent() {
        binding.apply {
            btnLogin.setOnClickListener {
                intentTo(applicationContext, MainActivity())
            }

            btnCtaRegister.setOnClickListener {
                intentTo(applicationContext, RegisterActivity())
            }
        }
    }

    private fun intentTo(context: Context, activity: Activity) {
        val intent = Intent(context, activity::class.java);
        startActivity(intent)
        finish()
    }
}