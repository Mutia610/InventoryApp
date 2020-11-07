package com.example.inventoryapp.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.inventoryapp.R
import com.example.inventoryapp.home.adapter.StockAdapter
import com.example.inventoryapp.local.Stocks
import com.example.inventoryapp.viewModel.ViewModelStock
import com.example.inventoryapp.viewModel.ViewModelUser
import kotlinx.android.synthetic.main.form_dialog_stock.view.*
import kotlinx.android.synthetic.main.fragment_stock.*
import kotlinx.android.synthetic.main.item_stock.view.*
import java.text.SimpleDateFormat
import java.util.*

class StockFragment : Fragment() {

    private var dialogView: Dialog? = null
    private lateinit var viewModelStock: ViewModelStock


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelStock = ViewModelProvider(this).get(ViewModelStock::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelStock.getListStock()
        attachObserve()

        fab.setOnClickListener {
            showAddDialog()
        }

    }

    private fun attachObserve() {
       //getData
        viewModelStock.responsesData.observe(
            viewLifecycleOwner, androidx.lifecycle.Observer { showStock(it) })

        viewModelStock.error.observe(
            viewLifecycleOwner, androidx.lifecycle.Observer { showError(it) })

        //input
        viewModelStock.responsesAksi.observe(
            viewLifecycleOwner, androidx.lifecycle.Observer { inputStock(it) })

        viewModelStock.error.observe(
            viewLifecycleOwner, androidx.lifecycle.Observer { inputError(it) })

    }

    private fun inputStock(it: Stocks) {
        Log.d("TAG", "inputStock : OK")
        //Toast.makeText(context, "Insert Data Berhasil", Toast.LENGTH_LONG).show()
        dialogView?.dismiss()
        viewModelStock.getListStock()
    }

    private fun inputError(it: Throwable?) {
        Toast.makeText(context, "Input Data Gagal", Toast.LENGTH_SHORT).show()
        Log.d("TAG", "inputStock : Gagal")
    }

    private fun showError(it: Throwable?) {
       // Toast.makeText(context, it?.message, Toast.LENGTH_SHORT).show()
        Log.d("TAG", "Show Stock : Gagal")

        Log.d("TAG", "showError: ${it?.message}")
    }

    private fun showStock(it: List<Stocks>) {

        val adapter = StockAdapter(it, object : StockAdapter.OnClickListener {
            override fun update(it: Stocks?) {
                showUpdateDialog(it)
            }

            override fun delete(item: Stocks?) {
                AlertDialog.Builder(context).apply {
                    setTitle("Hapus")
                    setMessage("Yakin hapus stock ?")
                    setCancelable(false)

                    setPositiveButton("Yakin") { dialogInterface, i ->
                        if (item != null){
                            viewModelStock.deleteStock(item)
                        }
                    }
                    setNegativeButton("Batal") { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }
                }.show()
            }
        })
        listStock.adapter = adapter
    }

    private fun showAddDialog() {
        val dialog = AlertDialog.Builder(context)
        val view = layoutInflater.inflate(R.layout.form_dialog_stock, null)
        dialog.setView(view)

//        view.tvDate.setText(getDate())
        view.btnSave.setOnClickListener {
            if (view.etNamaBarang.text.toString().isEmpty())
                view.etNamaBarang.error = "Nama Barang harus diisi"
            else if (view.etJumlahStock.text.toString().isEmpty())
                view.etJumlahStock.error = "Jumlah Stock Barang harus diisi"
            else if (view.etKeterangan.text.toString().isEmpty())
                view.etKeterangan.error = "Keterangan mengenai barang harus diisi"
            else{
                Toast.makeText(context, "Input Berhasil", Toast.LENGTH_SHORT).show()
                viewModelStock.insertStock(null, getDate(), view.etNamaBarang.text.toString(),view.etJumlahStock.text.toString(),view.etKeterangan.text.toString())
            }
        }

        view.back.setOnClickListener {
            dialogView?.dismiss()
        }

         dialogView = dialog.create()
         dialogView?.show()
    }

    private fun showUpdateDialog(item: Stocks?) {
        val dialog = AlertDialog.Builder(context)
        val view = layoutInflater.inflate(R.layout.form_dialog_stock, null)
        dialog.setView(view)

        view.btnSave.text = "update"

      //  view.tvDate.setText((item?.date))
        view.etNamaBarang.setText(item?.nama_barang)
        view.etJumlahStock.setText(item?.jumlah_stock)
        view.etKeterangan.setText(item?.keterangan)

        view.btnSave.setOnClickListener {
            if (view.etNamaBarang.text.toString().isEmpty())
                view.etNamaBarang.error = "Nama Barang harus diisi"
            else if (view.etJumlahStock.text.toString().isEmpty())
                view.etJumlahStock.error = "Jumlah Stock Barang harus diisi"
            else if (view.etKeterangan.text.toString().isEmpty())
                view.etKeterangan.error = "Keterangan mengenai barang harus diisi"
            else
                viewModelStock.updateStock(item?.id, getDate(), view.etNamaBarang.text.toString(),
                    view.etJumlahStock.text.toString(),
                    view.etKeterangan.text.toString())
               /* updateStock(
                    Stocks(
                        item?.id,
                        view.etJumlahStock.text.toString(),
                        view.etKeterangan.text.toString(),
                        view.etNamaBarang.text.toString(),
tDate()
                    )
                ) */
        }

        view.back.setOnClickListener {
            dialogView?.dismiss()
        }

        dialogView = dialog.create()
        dialogView?.show()
    }

    private fun getDate(): String {

        val date = Calendar.getInstance().time
        val format = SimpleDateFormat.getDateInstance()
        val formatDate = format.format(date)

        return formatDate
    }
}


