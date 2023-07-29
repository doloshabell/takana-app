package com.example.takana.presentation.money_account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.takana.data.model.response.DataAccount
import com.example.takana.data.util.toRupiah
import com.example.takana.databinding.ItemListTransactionBinding

class MoneyAccountListAdapter() : RecyclerView.Adapter<MoneyAccountListAdapter.ViewHolder>() {

    private val accountList: ArrayList<DataAccount> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListTransactionBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(accountList[position])
    }

    override fun getItemCount(): Int {
        return accountList.size
    }

    fun setData(dataAccount: List<DataAccount>) {
        accountList.clear()
        accountList.addAll(dataAccount)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemListTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataAccount: DataAccount) {
            binding.apply {
                tvItemCategory.text = dataAccount.accountName
                tvItemAccount.text = dataAccount.accountTypeName
                tvItemAmount.text = dataAccount.accountAmount.toRupiah()
                tvItemDate.visibility = View.GONE
            }
        }
    }

}