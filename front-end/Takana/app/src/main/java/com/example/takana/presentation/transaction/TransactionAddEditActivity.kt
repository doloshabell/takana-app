package com.example.takana.presentation.transaction

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.takana.R
import com.example.takana.databinding.ActivityTransactionAddEditBinding


class TransactionAddEditActivity : AppCompatActivity() {

    lateinit var binding: ActivityTransactionAddEditBinding
    lateinit var getThisIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        getThisIntent = intent
        setupContent()
    }

    private fun setupContent() {
        binding.apply {
            tvTitle.text =
                getString(R.string.add_or_edit_transaction, getThisIntent.getStringExtra("TODO_TRANSACTION"))
        }
    }
}