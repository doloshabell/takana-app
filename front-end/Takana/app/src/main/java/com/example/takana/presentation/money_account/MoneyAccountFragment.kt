package com.example.takana.presentation.money_account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.takana.MainActivity
import com.example.takana.R
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.DataAccount
import com.example.takana.data.model.response.GetAllAccountResponse
import com.example.takana.data.model.response.LoginResponse
import com.example.takana.data.util.SPAllAccount
import com.example.takana.data.util.SessionManager
import com.example.takana.databinding.FragmentMoneyAccountBinding

class MoneyAccountFragment : Fragment() {

    lateinit var binding: FragmentMoneyAccountBinding
    lateinit var token: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MoneyAccountListAdapter
    private val viewModel by viewModels<MoneyAccountViewModel>()
    private val accountList: ArrayList<DataAccount> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoneyAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        token = SessionManager.getToken(requireContext()).toString()
        viewModel.getAllAccountMoney(token)
        viewModelGetAllAccount()
        setupContent()
    }

    private fun setupContent() {
        binding.apply {
            btnAddAccountMoney.setOnClickListener {
                val intentToAddAccountMoney =
                    Intent(requireContext(), MoneyAccountAddEditActivity::class.java)
                intentToAddAccountMoney.putExtra("TODO_MONEY_ACCOUNT", "Add")
                startActivity(intentToAddAccountMoney)
            }
        }
    }

    private fun viewModelGetAllAccount() {
        viewModel.getAllAccountResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    processGetAllAccount(it.data)
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

    private fun processGetAllAccount(data: GetAllAccountResponse?) {
        showToast(data?.message.toString())
        SPAllAccount.saveAllAccount(requireContext(), data?.data!!)
        recyclerView = binding.rvListAccountMoney
        adapter = MoneyAccountListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        if (SPAllAccount.getAccountList(requireContext()).isNotEmpty()) {
            accountList.addAll(SPAllAccount.getAccountList(requireContext()))
            adapter.setData(accountList)
        }
        stopLoading()
    }

    fun processError(msg: String?) {
        showToast(msg.toString())
        stopLoading()
    }

    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}

