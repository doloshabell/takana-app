package com.example.takana.presentation.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.OnFocusChangeListener
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.takana.databinding.ActivityRegisterBinding
import com.example.takana.presentation.login.LoginActivity


class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupContent()
    }

    private fun setupContent() {
        binding.apply {
            setAutoComplete(binding.actvGender)

            btnCtaLogin.setOnClickListener {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setAutoComplete(autoCompleteTextView: AutoCompleteTextView) {
        val genders = resources.getStringArray(com.example.takana.R.array.gender_array)

        val adapterGenders =
            ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, genders)

        autoCompleteTextView.setAdapter(adapterGenders)

        autoCompleteTextView.setOnTouchListener { _, _ ->
            autoCompleteTextView.showDropDown()
            false
        }
        autoCompleteTextView.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                autoCompleteTextView.showDropDown()
            }
        }
    }
}