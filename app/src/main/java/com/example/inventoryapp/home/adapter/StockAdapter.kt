package com.example.inventoryapp.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventoryapp.R
import com.example.inventoryapp.local.Stocks
import kotlinx.android.synthetic.main.item_stock.view.*

class StockAdapter(
    private val data: List<Stocks>?,
    private val itemClick: OnClickListener
) : RecyclerView.Adapter<StockAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : Stocks? = data?.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    class ViewHolder(
        val view: View,
        val itemClick: OnClickListener
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: Stocks?) {
            view.tvDate.text = item?.date
            view.tvIsiNamaBarang.text = item?.nama_barang
            view.tvIsiJmlStock.text = item?.jumlah_stock
            view.tvIsiKeterangan.text = item?.keterangan

            view.btnUpdate.setOnClickListener {
                itemClick.update(item)
            }

            view.btnDelete.setOnClickListener {
                itemClick.delete(item)
            }
        }
    }

    interface OnClickListener {
        fun update(item: Stocks?)
        fun delete(item: Stocks?)
    }

}