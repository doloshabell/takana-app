package com.example.takana.presentation.profile

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.takana.MainActivity
import com.example.takana.R
import com.example.takana.data.model.response.AddResponse
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.GetDetailProfileResponse
import com.example.takana.data.util.SessionManager
import com.example.takana.data.util.UserToken
import com.example.takana.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditProfileBinding
    lateinit var token: String
    lateinit var user: UserToken.User
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        token = SessionManager.getToken(applicationContext).toString()
        user = UserToken.getObjectFromSharedPreferences(applicationContext)!!
        viewModel.getDetailProfile(token, user.userId!!.toLong())
        viewModelGetDetailProfile()
        setupContent()
    }

    private fun setupContent() {
        setSpinnerGenderTypes()
        binding.apply {
            btnUpdateProfile.setOnClickListener {
                viewModel.updateDataProfile(
                    token,
                    user.userId?.toInt()!!,
                    etEmail.text.toString(),
                    etUsername.text.toString(),
                    etPassword.text.toString(),
                    etFullName.text.toString(),
                    etPhoneNumber.text.toString(),
                    etDomicile.text.toString(),
                    spinnerGenderType.selectedItem.toString(),
                    "",
                    "",
                    0
                )
                viewModelUpdateDataProfile()
            }
            btnDeleteProfile.setOnClickListener {
                viewModel.deleteDataProfile(
                    token,
                    user.userId?.toInt()!!,
                    etEmail.text.toString(),
                    etUsername.text.toString(),
                    etPassword.text.toString(),
                    etFullName.text.toString(),
                    etPhoneNumber.text.toString(),
                    etDomicile.text.toString(),
                    spinnerGenderType.selectedItem.toString(),
                    "",
                    "",
                    0
                )
                viewModelDeleteDataProfile()
            }
        }
    }

    private fun setSpinnerGenderTypes() {
        val itemAccountType = resources.getStringArray(R.array.gender_types)
        val adapterAccountType = object : ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_spinner_item,
            itemAccountType
        ) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView =
                    super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
                    view.setTextColor(Color.DKGRAY)
                }
                return view
            }
        }
        adapterAccountType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.apply {
            spinnerGenderType.adapter = adapterAccountType
            spinnerGenderType.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val value = parent!!.getItemAtPosition(position).toString()
                        if (value == itemAccountType[0]) {
                            (view as TextView).setTextColor(Color.GRAY)
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    private fun viewModelGetDetailProfile() {
        viewModel.getDetailProfileResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    processGetDetail(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }

                else -> {
                    stopLoading()
                }
            }
        }
    }

    private fun viewModelUpdateDataProfile() {
        viewModel.updateDataProfileResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    processUpdateOrDeleteDataProfile(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }

                else -> {
                    stopLoading()
                }
            }
        }
    }

    private fun viewModelDeleteDataProfile() {
        viewModel.deleteDataProfileResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    processUpdateOrDeleteDataProfile(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }

                else -> {
                    stopLoading()
                }
            }
        }
    }

    private fun processGetDetail(data: GetDetailProfileResponse?) {
        showToast(data?.message.toString())
        stopLoading()
        binding.apply {
            etFullName.setText(data?.data!!.fullName)
            etUsername.isEnabled = false
            etUsername.setText(data.data.username)
            etEmail.setText(data.data.email)
            etPassword.setText("********")
            etPhoneNumber.setText(data.data.phoneNumber)
            etDomicile.setText(data.data.address)
        }
    }

    private fun processUpdateOrDeleteDataProfile(data: AddResponse?) {
        showToast(data?.message.toString())
        stopLoading()
        goToProfile()
    }

    private fun showLoading() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        binding.pbLoading.visibility = View.GONE
    }

    fun processError(msg: String?) {
        showToast(msg.toString())
        stopLoading()
    }

    fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    private fun goToProfile() {
        val intent = Intent(applicationContext, MainActivity::class.java);
        intent.putExtra("FROM", "PROFILE_EDIT")
        startActivity(intent)
        finish()
    }
}