package com.example.takana.presentation.transaction

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.takana.MainActivity
import com.example.takana.R
import com.example.takana.data.model.response.AddResponse
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.DataAccount
import com.example.takana.data.model.response.GetDetailTransactionResponse
import com.example.takana.data.util.SPAllAccount
import com.example.takana.data.util.SessionManager
import com.example.takana.data.util.toRupiah
import com.example.takana.databinding.ActivityTransactionAddEditBinding
import java.text.DecimalFormat
import java.util.Calendar


class TransactionAddEditActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    lateinit var binding: ActivityTransactionAddEditBinding
    lateinit var getThisIntent: Intent
    lateinit var token: String
    private var accountList: ArrayList<DataAccount> = ArrayList()
    private val viewModel by viewModels<TransactionViewModel>()
    var fromAccountId: Int = 0
    var toAccountId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        getThisIntent = intent
        token = SessionManager.getToken(applicationContext).toString()
        accountList.addAll(SPAllAccount.getAccountList(applicationContext))
        setupContent()
    }

    private fun setupContent() {
        binding.apply {
            tvTransactionDate.setOnClickListener {
                showDatePickerDialog()
            }

            tvTransactionTime.setOnClickListener {
                showTimePicker()
            }

            if (getThisIntent.getStringExtra("TODO_TRANSACTION").toString() == "Add") {
                setSpinnerTransactionTypes()
                setSpinnerTransactionCategoryTypes()
                setSpinnerFromAccount()
                setSpinnerToAccount()
                tvTitle.text =
                    getString(
                        R.string.add_or_edit_transaction,
                        "Tambah"
                    )
                btnSaveTransaction.setOnClickListener {
                    val spinnerTransactionToAccPosition =
                        if (spinnerTransactionType.selectedItemPosition != 3) 0 else toAccountId
                    Log.d(
                        "TAG",
                        "setupContent: $spinnerTransactionToAccPosition & " + spinnerTransactionToAcc.selectedItemPosition
                    )
                    val date: String = tvTransactionDate.text.toString()
                    val time: String = tvTransactionTime.text.toString()
                    val dateTime = date.plus("T").plus(time).plus(":00Z")
                    viewModel.addDataTransaction(
                        token,
                        spinnerTransactionType.selectedItemPosition,
                        spinnerTransactionCategory.selectedItemPosition,
                        etTransactionAmount.text.toString().toInt(),
                        etTransactionNote.text.toString(),
                        fromAccountId,
                        spinnerTransactionToAccPosition,
                        dateTime
                    )
                    viewModelAddDataTransaction()
                }
            } else if (getThisIntent.getStringExtra("TODO_TRANSACTION").toString() == "Edit") {
                tvTitle.text =
                    getString(
                        R.string.add_or_edit_transaction,
                        "Detail"
                    )
                viewModel.getDetailTransaction(
                    token,
                    getThisIntent.getIntExtra("ID_TRANSACTION", 0).toLong()
                )
                viewModelGetDetailTransaction()
                etTransactionAmount.isEnabled = false
                spinnerTransactionType.isEnabled = false
                spinnerTransactionCategory.isEnabled = false
                spinnerTransactionFromAcc.isEnabled = false
                spinnerTransactionToAcc.isEnabled = false
                tvTransactionDate.isEnabled = false
                tvTransactionTime.isEnabled = false
                etTransactionNote.isEnabled = false
                btnSaveTransaction.text = "Hapus Transaksi"
            }
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
                        if (position == 3)
                            spinnerTransactionToAcc.visibility = View.VISIBLE
                        else
                            spinnerTransactionToAcc.visibility = View.GONE
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
        val itemAccountName: List<String> = accountList.map { it.accountName }
        val adapterTransactionCategoryType = object : ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_spinner_item,
            itemAccountName
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
            spinnerTransactionFromAcc.adapter = adapterTransactionCategoryType
            spinnerTransactionFromAcc.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        /*val value = parent!!.getItemAtPosition(position).toString()
                        if (value == itemTransactionCategoryType[0]) {
                            (view as TextView).setTextColor(Color.GRAY)
                        }*/
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
                        val selectedToAccount: DataAccount? =
                            adapterTransactionToAccount.getItem(position)
                        if (selectedToAccount != null) {
                            toAccountId = selectedToAccount.accountId
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    private fun viewModelGetDetailTransaction() {
        viewModel.getDetailTransactionsResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    processGetDetailTransaction(it.data)
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

    private fun viewModelDeleteDataTransaction() {
        viewModel.deleteDataTransactionResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    processAddorDeleteDataTransaction(it.data)
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

    private fun viewModelAddDataTransaction() {
        viewModel.addDataTransactionResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    processAddorDeleteDataTransaction(it.data)
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

    private fun processGetDetailTransaction(data: GetDetailTransactionResponse?) {
        showToast(data?.message.toString())
        stopLoading()
        binding.apply {
            setSpinnerTransactionTypes()
            setSpinnerTransactionCategoryTypes()
            etTransactionAmount.setText(data?.data?.amount?.toLong().toRupiah())
            spinnerTransactionType.setSelection(data?.data?.transactionType!!)
            spinnerTransactionCategory.setSelection(data.data.categoryId)
            /*spinnerTransactionFromAcc.setSelection(data.data.fromAccountId)
            if (data.data.toAccountId != 0 || data.data.toAccountId != null) {
                setSpinnerToAccount()
                spinnerTransactionToAcc.visibility = View.VISIBLE
                spinnerTransactionToAcc.setSelection(data.data.toAccountId)
            }*/
            tvTransactionDate.text = data.data.transactionDate
            tvTransactionTime.text = data.data.transactionDate
            etTransactionNote.setText(data.data.note)
            btnSaveTransaction.setOnClickListener {
                viewModel.deleteDataTransaction(
                        token,
                    data.data.transactionId,
                    data.data.transactionCode,
                    data.data.transactionType,
                    data.data.transactionDate,
                        data.data.amount,
                    data.data.fromAccountId,
                    data.data.toAccountId,
                    data.data.accountName,
                    data.data.categoryId,
                    data.data.categoryName,
                    data.data.note,
                    data.data.deletedAt,
                    data.data.userId
                    )
                    viewModelDeleteDataTransaction()
            }
        }
    }

    private fun processAddorDeleteDataTransaction(data: AddResponse?) {
        showToast(data?.message.toString())
        stopLoading()
        goToTransaction()
    }

    private fun goToTransaction() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("FROM", "TRANSACTION_ADD_EDIT")
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

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(
            this, this,
            year, month, dayOfMonth
        )
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val decimalFormat = DecimalFormat("00")
        var selectedDate: String =
            (year.toString() + "-" + decimalFormat.format(month + 1)) + "-" + decimalFormat.format(
                dayOfMonth
            )
        binding.tvTransactionDate.text = selectedDate
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]

        val timePickerDialog = TimePickerDialog(
            this,
            this,
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
        binding.tvTransactionTime.text = selectedTime
    }
}