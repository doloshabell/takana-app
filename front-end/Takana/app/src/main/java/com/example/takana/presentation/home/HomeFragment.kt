package com.example.takana.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.takana.MainActivity
import com.example.takana.R
import com.example.takana.data.util.UserToken
import com.example.takana.databinding.FragmentHomeBinding
import com.example.takana.presentation.money_account.MoneyAccountFragment
import com.example.takana.presentation.transaction.TransactionFragment
import kotlin.apply

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setupContent(requireContext())
        return binding.root
    }

    private fun setupContent(context: Context) {
        val user: UserToken.User? = UserToken.getObjectFromSharedPreferences(requireContext())
        if (context is MainActivity) {
            binding.apply {
                tvGreetUser.text = getString(R.string.halo_user, user?.username)
                tvToMoneyAccount.setOnClickListener {
                    context.binding.bnvMenu.selectedItemId = R.id.account
                    context.replaceFragment(MoneyAccountFragment())
                }
                btnToTransaction.setOnClickListener {
                    context.binding.bnvMenu.selectedItemId = R.id.transactions
                    context.replaceFragment(TransactionFragment())
                }
            }
        }
    }
}
