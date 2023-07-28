package com.example.takana.presentation.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.takana.MainActivity
import com.example.takana.R
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.LoginResponse
import com.example.takana.data.model.response.RegisterResponse
import com.example.takana.data.util.SessionManager
import com.example.takana.databinding.ActivityRegisterBinding
import com.example.takana.presentation.login.LoginActivity
import com.example.takana.presentation.login.LoginViewModel


class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupContent()
        viewModelRegister()
    }

    private fun setupContent() {
        binding.apply {
            btnRegister.setOnClickListener {
                if (etEmail.text.isNullOrBlank())
                    etEmail.error = "Diisi dulu yuk email kamu"
                else if (etUsername.text.isNullOrBlank())
                    etUsername.error = getString(R.string.username_kosong)
                else if (etFullname.text.isNullOrBlank())
                    etFullname.error = "Diisi dulu yuk Nama Lengkap Kamu"
                else if (etPassword.text.isNullOrBlank())
                    etPassword.error = getString(R.string.password_kosong)
                else if (etPhoneNumber.text.isNullOrBlank())
                    etPhoneNumber.error = "Diisi dulu yuk Nomor Telepon"
                else {
                    showLoading()
                    viewModel.registerUser(
                        etEmail.text.toString(),
                        etFullname.text.toString(),
                        etPassword.text.toString(),
                        etPhoneNumber.text.toString(),
                        etUsername.text.toString()
                    )
                }
            }
            btnCtaLogin.setOnClickListener {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
    }

    private fun viewModelRegister() {
        viewModel.registerResult.observe(this) {
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

    private fun processLogin(data: RegisterResponse?) {
        showToast("Success:" + data?.message)
        stopLoading()
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun processError(msg: String?) {
        showToast("Error:" + msg)
        stopLoading()
    }

    fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}