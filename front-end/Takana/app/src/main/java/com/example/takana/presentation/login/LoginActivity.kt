package com.example.takana.presentation.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.takana.MainActivity
import com.example.takana.R
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.LoginResponse
import com.example.takana.data.util.SessionManager
import com.example.takana.databinding.ActivityLoginBinding
import com.example.takana.presentation.register.RegisterActivity
import com.example.takana.service.JwtDecode

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupContent()
        viewModelLogin()
    }

    private fun setupContent() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (etUsername.text.isNullOrBlank()) {
                    etUsername.error = getString(R.string.username_kosong)
                } else if (etPassword.text.isNullOrBlank()) {
                    etPassword.error = getString(R.string.password_kosong)
                } else {
                    showLoading()
                    viewModel.logInUser(etUsername.text.toString(), etPassword.text.toString())
                }
            }

            btnCtaRegister.setOnClickListener {
                intentTo(applicationContext, RegisterActivity())
            }
        }
    }

    private fun intentTo(context: Context, activity: Activity) {
        val intent = Intent(context, activity::class.java);
        startActivity(intent)
    }

    private fun viewModelLogin() {
        viewModel.logInResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    processLogin(it.data)
                }

                is BaseResponse.Error -> {
                    stopLoading()
                    processError(it.msg)
                }

                else -> {
                    stopLoading()
                }
            }
        }
    }

    private fun showLoading() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        binding.pbLoading.visibility = View.GONE
    }

    private fun processLogin(data: LoginResponse?) {
        showToast(data?.message.toString())
        stopLoading()
        if (!data?.data?.AccessToken.isNullOrEmpty()) {
            data?.data?.AccessToken?.let {
                SessionManager.saveAuthToken(this, it)
                JwtDecode().decodeJwt(this, it)
            }
        }
        intentTo(applicationContext, MainActivity())
    }

    fun processError(msg: String?) {
        showToast(msg.toString())
        stopLoading()
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}