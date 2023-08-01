package com.example.takana.presentation.transaction

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.takana.R
import com.example.takana.data.model.response.DataAllTransaction
import com.example.takana.data.util.toRupiah
import kotlinx.android.synthetic.main.item_list_transaction.view.*

class TransactionListAdapter(
    var transactionLists: ArrayList<DataAllTransaction>,
    val listener: OnAdapterListener
) : RecyclerView.Adapter<TransactionListAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_transaction,
            parent, false
        )
    )

    override fun getItemCount(): Int {
        return transactionLists.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transactionItem = transactionLists[position]
//        var dateString = transactionItem.transactionDate;
//        var odt = OffsetDateTime.parse(dateString);
//        var dtf = DateTimeFormatter.ofPattern("MMM dd, uuuu", Locale.ENGLISH)
//        dtf.format(odt)
        holder.view.tv_item_category.text = transactionItem.categoryName
        holder.view.tv_item_account.text = transactionItem.accountName
        holder.view.tv_item_amount.text = transactionItem.amount.toLong().toRupiah()
        holder.view.tv_item_date.text = transactionItem.transactionDate
    }

    fun setData(dataTransactions: List<DataAllTransaction>) {
        transactionLists.clear()
        transactionLists.addAll(dataTransactions)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(dataTransaction: DataAllTransaction)
    }
}