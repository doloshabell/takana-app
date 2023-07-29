package com.example.takana.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.takana.R
import com.example.takana.data.util.UserToken
import com.example.takana.data.util.UserToken.getObjectFromSharedPreferences
import com.example.takana.databinding.FragmentHomeBinding
import com.example.takana.presentation.money_account.MoneyAccountFragment
import com.example.takana.presentation.transaction.TransactionFragment
import kotlin.apply

class HomeFragment : Fragment() {

    private lateinit var navController: NavController
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setupContent()
    }

    private fun setupContent() {
        val user: UserToken.User? = getObjectFromSharedPreferences(requireContext())
        binding.apply {
            tvGreetUser.text = getString(R.string.halo_user, user?.username)
            /*tvToMoneyAccount.setOnClickListener {
                val toMoneyAccount = requireActivity().supportFragmentManager.beginTransaction()
                toMoneyAccount.replace(R.id.fl_activity, MoneyAccountFragment())
                toMoneyAccount.disallowAddToBackStack()
                toMoneyAccount.commit()
            }*/

            btnToTransaction.setOnClickListener {
                navController.navigate(R.id.ac)
            }
        }
    }
}
