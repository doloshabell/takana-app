package com.example.takana.presentation.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.takana.MainActivity
import com.example.takana.R
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.GetDetailProfileResponse
import com.example.takana.data.model.response.LoginResponse
import com.example.takana.data.util.SPAllAccount
import com.example.takana.data.util.SessionManager
import com.example.takana.data.util.UserToken
import com.example.takana.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()
    lateinit var token: String
    lateinit var user: UserToken.User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContent()
        token = SessionManager.getToken(requireContext()).toString()
        user = UserToken.getObjectFromSharedPreferences(requireContext())!!
        viewModel.getDetailProfile(token, user.userId!!.toLong())
        viewModelGetDetailProfile()
    }

    private fun setupContent() {
        val user: UserToken.User? = UserToken.getObjectFromSharedPreferences(requireContext())
        binding.apply {
            tvEditProfile.setOnClickListener {
                val intent = Intent(requireContext(), EditProfileActivity::class.java)
                startActivity(intent)
            }

            tvLogout.setOnClickListener {
                viewModel.logOutUser(token)
                viewModelLogOut()
            }
        }
    }

    private fun viewModelLogOut() {
        viewModel.logOutResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    processLogOut(it.data)
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

    private fun showLoading() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        binding.pbLoading.visibility = View.GONE
    }

    private fun processLogOut(data: LoginResponse?) {
        showToast(data?.message.toString())
        stopLoading()
        SessionManager.clearDataSession(requireContext())
        SPAllAccount.clearDataAccount(requireContext())
        UserToken.clearDataUserToken(requireContext())
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }

    fun processError(msg: String?) {
        showToast(msg.toString())
        stopLoading()
    }

    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
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

    private fun processGetDetail(data: GetDetailProfileResponse?) {
        showToast(data?.message.toString())
        stopLoading()
        binding.apply {
            tvFullName.text = getString(R.string.one_string, data?.data?.fullName)
            tvUsername.text = getString(R.string.username_x, data?.data?.username)
        }
    }

}