package com.example.takana.presentation.transaction

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.takana.data.model.response.DataAccount
import com.example.takana.data.model.response.DataAllTransaction
import com.example.takana.data.util.SessionManager
import com.example.takana.databinding.FragmentTransactionBinding
import com.example.takana.presentation.money_account.MoneyAccountListAdapter
import com.example.takana.presentation.money_account.MoneyAccountViewModel

class TransactionFragment : Fragment() {

    lateinit var binding: FragmentTransactionBinding
    lateinit var token: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionListAdapter
    private val viewModel by viewModels<MoneyAccountViewModel>()
    private val transactionList: ArrayList<DataAllTransaction> = ArrayList()

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

}