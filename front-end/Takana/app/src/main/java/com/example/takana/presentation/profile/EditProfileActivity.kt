package com.example.takana.presentation.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.takana.R
import com.example.takana.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupContent()
    }

    private fun setupContent() {
        binding.apply {
            setAutoComplete(actvGender)
        }
    }

    private fun setAutoComplete(autoCompleteTextView: AutoCompleteTextView) {
        val genders = resources.getStringArray(R.array.gender_array)

        val adapterGenders =
            ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, genders)

        autoCompleteTextView.setAdapter(adapterGenders)

        autoCompleteTextView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                autoCompleteTextView.showDropDown()
            }
        }
    }
}