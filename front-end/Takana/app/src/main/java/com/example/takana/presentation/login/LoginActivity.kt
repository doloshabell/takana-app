package com.example.takana.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            btnCtaRegister.setOnClickListener {
                val intent = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}