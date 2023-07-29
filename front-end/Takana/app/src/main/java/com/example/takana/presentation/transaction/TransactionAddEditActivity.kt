package com.example.takana.presentation.transaction

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.takana.R
import com.example.takana.databinding.ActivityAddEditTransactionBinding


class TransactionAddEditActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddEditTransactionBinding
    lateinit var getThisIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        getThisIntent = intent
        setupContent()
    }

    private fun setupContent() {
        binding.apply {
            tvTitle.text =
                getString(
                    R.string.add_or_edit_transaction,
                    getThisIntent.getStringExtra("TODO_TRANSACTION")
                )
        }
    }
}