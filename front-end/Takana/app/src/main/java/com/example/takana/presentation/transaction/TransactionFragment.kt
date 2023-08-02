package com.example.takana.presentation.transaction

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.DataAllTransaction
import com.example.takana.data.model.response.GetAllTransactionsResponse
import com.example.takana.data.util.SessionManager
import com.example.takana.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment() {

    lateinit var binding: FragmentTransactionBinding
    lateinit var token: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionListAdapter
    private val viewModel by viewModels<TransactionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        token = SessionManager.getToken(requireContext()).toString()
        viewModel.getAllTransaction(token)
        viewModelGetAllTransaction()
        setupContent()
    }

    private fun setupContent() {
        binding.apply {
            btnAddTransaction.setOnClickListener {
                val intentToAddTransaction =
                    Intent(requireContext(), TransactionAddEditActivity::class.java)
                intentToAddTransaction.putExtra("TODO_TRANSACTION", "Add")
                startActivity(intentToAddTransaction)
            }
        }
    }

    private fun viewModelGetAllTransaction() {
        viewModel.getAllTransactionResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    processGetAllTransaction(it.data)
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

    private fun processGetAllTransaction(data: GetAllTransactionsResponse?) {
        showToast(data?.message.toString())
        recyclerView = binding.rvListTransaction
        adapter = TransactionListAdapter(
            arrayListOf(),
            object : TransactionListAdapter.OnAdapterListener {
                override fun onClick(dataTransaction: DataAllTransaction) {
                    startActivity(
                        Intent(requireContext(), TransactionAddEditActivity()::class.java)
                            .putExtra("TODO_TRANSACTION", "Edit")
                            .putExtra("ID_TRANSACTION", dataTransaction.transactionId)
                    )
                }
            })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.setData(data?.data!!)
        stopLoading()
    }

    private fun showLoading() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        binding.pbLoading.visibility = View.GONE
    }

    private fun processError(msg: String?) {
        showToast(msg.toString())
        stopLoading()
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}