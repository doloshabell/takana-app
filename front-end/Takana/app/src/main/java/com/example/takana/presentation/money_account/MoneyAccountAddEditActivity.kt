package com.example.takana.presentation.money_account

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginEnd
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.takana.MainActivity
import com.example.takana.R
import com.example.takana.data.model.response.AddResponse
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.util.SessionManager
import com.example.takana.data.util.UserToken
import com.example.takana.databinding.ActivityMoneyAccountsAddEditBinding


class MoneyAccountAddEditActivity : AppCompatActivity() {

    lateinit var binding: ActivityMoneyAccountsAddEditBinding
    lateinit var getThisIntent: Intent
    lateinit var token: String
    var accountTypeId: Int = 0
    private val viewModel by viewModels<MoneyAccountViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoneyAccountsAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        getThisIntent = intent
        token = SessionManager.getToken(applicationContext).toString()
        setupContent()
        viewModelAddMoneyAccount()
    }

    private fun setupContent() {
        setSpinnerAccountType()
        val user: UserToken.User? = UserToken.getObjectFromSharedPreferences(applicationContext)
        binding.apply {
            tvTitle.text = getString(
                R.string.add_or_edit_money_account,
                getThisIntent.getStringExtra("TODO_MONEY_ACCOUNT")
            )
            if (getThisIntent.getStringExtra("TODO_MONEY_ACCOUNT").toString() == "Add") {
                btnSaveMoneyAccount.setOnClickListener {
                    viewModel.addAccountMoney(
                        token,
                        etAccountName.text.toString(),
                        etAccountAmount.text.toString().toLong(),
                        accountTypeId,
                        user?.userId!!,
                        etBankAccountNumber.text.toString().toIntOrNull()
                    )
                }
            } else if (getThisIntent.getStringExtra("TODO_MONEY_ACCOUNT").toString() == "Edit") {
                btnDeleteMoneyAccount.visibility = View.VISIBLE
                btnDeleteMoneyAccount.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    setMargins(0, 60, 30, 0)
                }
                btnSaveMoneyAccount.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    setMargins(30, 60, 0, 0)
                }
                showToast("HAI EDIT")
            }
        }
    }

    private fun setSpinnerAccountType() {
        val itemAccountType = resources.getStringArray(R.array.account_type)
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
            spinnerAccountType.adapter = adapterAccountType
            spinnerAccountType.onItemSelectedListener =
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
                        accountTypeId = position
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    private fun viewModelAddMoneyAccount() {
        viewModel.addDataMoneyAccountResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    processAddMoneyAccount(it.data)
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

    private fun processAddMoneyAccount(data: AddResponse?) {
        showToast(data?.message.toString())
        stopLoading()
        val intent = Intent(applicationContext, MainActivity::class.java);
        intent.putExtra("FROM", "MONEY_ACCOUNT_ADD_EDIT")
        startActivity(intent)
        finish()
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

}