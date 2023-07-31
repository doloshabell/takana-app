package com.example.takana.presentation.money_account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.takana.R
import com.example.takana.data.model.response.DataAccount
import com.example.takana.data.util.toRupiah
import kotlinx.android.synthetic.main.item_list_transaction.view.tv_item_account
import kotlinx.android.synthetic.main.item_list_transaction.view.tv_item_amount
import kotlinx.android.synthetic.main.item_list_transaction.view.tv_item_category
import kotlinx.android.synthetic.main.item_list_transaction.view.tv_item_date

class MoneyAccountListAdapter(
    var accountLists: ArrayList<DataAccount>,
    val listener: OnAdapterListener
) : RecyclerView.Adapter<MoneyAccountListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_transaction,
            parent, false
        )
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val accountList = accountLists[position]
        holder.view.tv_item_category.text = accountList.accountName
        holder.view.tv_item_account.text = accountList.accountTypeName
        holder.view.tv_item_amount.text = accountList.accountAmount.toRupiah()
        holder.view.tv_item_date.visibility = View.GONE
        holder.view.setOnClickListener {
            listener.onClick(accountList)
        }
    }

    override fun getItemCount(): Int {
        return accountLists.size
    }

    fun setData(dataAccount: List<DataAccount>) {
        accountLists.clear()
        accountLists.addAll(dataAccount)
        notifyDataSetChanged()
    }

    class ViewHolder(val view: View) :
        RecyclerView.ViewHolder(view)

    interface OnAdapterListener {
        fun onClick(accountData: DataAccount)
    }
}