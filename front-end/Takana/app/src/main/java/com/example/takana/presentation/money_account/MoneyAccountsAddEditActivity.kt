package com.example.takana.presentation.money_account

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.takana.R
import com.example.takana.databinding.ActivityMoneyAccountsAddEditBinding

class MoneyAccountAddEditActivity : AppCompatActivity() {

    lateinit var binding: ActivityMoneyAccountsAddEditBinding
    lateinit var getThisIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoneyAccountsAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        getThisIntent = intent
        setupContent()
    }

    private fun setupContent() {
        binding.apply {
            tvTitle.text = getString(
                R.string.add_or_edit_money_account,
                getThisIntent.getStringExtra("TODO_MONEY_ACCOUNT")
            )
        }
        setSpinnerAccountType()
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
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    override fun onBackPressed() {
        super.getOnBackPressedDispatcher().onBackPressed()
    }
}