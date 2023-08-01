package com.example.takana.presentation.home

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.takana.MainActivity
import com.example.takana.R
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.GetDetailProfileResponse
import com.example.takana.data.util.SessionManager
import com.example.takana.data.util.UserToken
import com.example.takana.data.util.toRupiah
import com.example.takana.databinding.FragmentHomeBinding
import com.example.takana.presentation.money_account.MoneyAccountFragment
import com.example.takana.presentation.profile.ProfileViewModel
import com.example.takana.presentation.transaction.TransactionFragment
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.apply

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val viewModelProfile by viewModels<ProfileViewModel>()
    lateinit var token: String
    lateinit var user: UserToken.User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        token = SessionManager.getToken(requireContext()).toString()
        user = UserToken.getObjectFromSharedPreferences(requireContext())!!
        viewModelProfile.getDetailProfile(token, user.userId!!.toLong())

        viewModelGetDetailProfile()
        setupContent(requireContext())
    }

    private fun setupContent(context: Context) {
        if (context is MainActivity) {
            binding.apply {
                tvGreetUser.text = getString(R.string.halo_user, "")
                tvToMoneyAccount.setOnClickListener {
                    context.binding.bnvMenu.selectedItemId = R.id.account
                    context.replaceFragment(MoneyAccountFragment())
                }
                btnToTransaction.setOnClickListener {
                    context.binding.bnvMenu.selectedItemId = R.id.transactions
                    context.replaceFragment(TransactionFragment())
                }
            }
        }
    }

    private fun viewModelGetDetailProfile() {
        viewModelProfile.getDetailProfileResult.observe(this) {
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
            tvGreetUser.text = getString(R.string.halo_user, data?.data?.fullName)
            tvBalance.text = data?.data?.totalAmount?.toLong().toRupiah()
        }
    }

    fun processError(msg: String?) {
        showToast(msg.toString())
        stopLoading()
    }

    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        binding.pbLoading.visibility = View.GONE
    }
}
