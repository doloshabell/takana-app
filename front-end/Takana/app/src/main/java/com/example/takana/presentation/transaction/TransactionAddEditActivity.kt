package com.example.takana.presentation.transaction

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.takana.R
import com.example.takana.data.model.response.DataAccount
import com.example.takana.data.util.SPAllAccount
import com.example.takana.databinding.ActivityTransactionAddEditBinding


class TransactionAddEditActivity : AppCompatActivity() {

    lateinit var binding: ActivityTransactionAddEditBinding
    lateinit var getThisIntent: Intent
    private var accountList: ArrayList<DataAccount> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        getThisIntent = intent
        accountList.addAll(SPAllAccount.getAccountList(applicationContext))
        setupContent()
    }

    private fun setupContent() {
        setSpinnerTransactionTypes()
        setSpinnerTransactionCategoryTypes()
        setSpinnerFromAccount()
        binding.apply {
            tvTitle.text =
                getString(
                    R.string.add_or_edit_transaction,
                    getThisIntent.getStringExtra("TODO_TRANSACTION")
                )

            /*if (spinnerTransactionType.isSelected) {
                Toast.makeText(
                    applicationContext,
                    spinnerTransactionType.selectedItemPosition,
                    Toast.LENGTH_SHORT
                ).show()
            } else
                Toast.makeText(
                    applicationContext,
                    spinnerTransactionType.selectedItemPosition,
                    Toast.LENGTH_SHORT
                ).show()*/

        }
    }

    private fun setSpinnerTransactionTypes() {
        val itemTransactionType = resources.getStringArray(R.array.transaction_types)
        val adapterTransctionType = object : ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_spinner_item,
            itemTransactionType
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
        adapterTransctionType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.apply {
            spinnerTransactionType.adapter = adapterTransctionType
            spinnerTransactionType.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val value = parent!!.getItemAtPosition(position).toString()
                        if (value == itemTransactionType[0]) {
                            (view as TextView).setTextColor(Color.GRAY)
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    private fun setSpinnerTransactionCategoryTypes() {
        val itemTransactionCategoryType =
            resources.getStringArray(R.array.transaction_category_types)
        val adapterTransactionCategoryType = object : ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_spinner_item,
            itemTransactionCategoryType
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
        adapterTransactionCategoryType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.apply {
            spinnerTransactionCategory.adapter = adapterTransactionCategoryType
            spinnerTransactionCategory.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val value = parent!!.getItemAtPosition(position).toString()
                        if (value == itemTransactionCategoryType[0]) {
                            (view as TextView).setTextColor(Color.GRAY)
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    private fun setSpinnerFromAccount() {
        val adapterTransactionCategoryType = object : ArrayAdapter<DataAccount>(
            applicationContext,
            android.R.layout.simple_spinner_item,
            accountList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                (view.findViewById(android.R.id.text1) as TextView).text =
                    getItem(position)?.accountName
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView =
                    super.getDropDownView(position, convertView, parent) as TextView
                view.text = getItem(position)!!.accountName
                if (position == 0) {
                    view.setTextColor(Color.DKGRAY)
                }
                return view
            }
        }
        adapterTransactionCategoryType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.apply {
            spinnerTransactionFromAcc.adapter = adapterTransactionCategoryType
            spinnerTransactionFromAcc.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val value = parent!!.getItemAtPosition(position).toString()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    private fun setSpinnerToAccount() {
        val adapterTransactionToAccount = object : ArrayAdapter<DataAccount>(
            applicationContext,
            android.R.layout.simple_spinner_item,
            accountList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                (view.findViewById(android.R.id.text1) as TextView).text =
                    getItem(position)?.accountName
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView =
                    super.getDropDownView(position, convertView, parent) as TextView
                view.text = getItem(position)!!.accountName
                if (position == 0) {
                    view.setTextColor(Color.DKGRAY)
                }
                return view
            }
        }
        adapterTransactionToAccount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.apply {
            spinnerTransactionToAcc.adapter = adapterTransactionToAccount
            spinnerTransactionToAcc.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val value = parent!!.getItemAtPosition(position).toString()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }
}