package com.example.takana.presentation.transaction

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.takana.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment() {

    lateinit var binding: FragmentTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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