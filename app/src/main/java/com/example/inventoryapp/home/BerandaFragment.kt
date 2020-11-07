package com.example.inventoryapp.home

import android.content.ContentValues
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import com.example.inventoryapp.R
import com.example.inventoryapp.helper.SessionManager
import com.example.inventoryapp.local.DatabaseStocks
import com.example.inventoryapp.viewModel.ViewModelStock
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.fragment_register1.*
import kotlinx.android.synthetic.main.fragment_register1.back
import kotlinx.android.synthetic.main.fragment_register2.*
import com.example.inventoryapp.viewModel.ViewModelUser
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_result.*

class BerandaFragment : Fragment() {

    private var stockDatabase: DatabaseStocks? = null
    private lateinit var viewModelUser: ViewModelUser
    private var session: SessionManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          stockDatabase = context?.let { DatabaseStocks.getInstance(it) }
          viewModelUser = ViewModelProvider(this).get(ViewModelUser::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNameSore()
    }

    private fun getNameSore() {
        Observable.fromCallable { stockDatabase?.stocksDao()?.getTotal() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                tvName.setText("$it buah")
                Log.d(ContentValues.TAG, "$it")
            }, {
                Log.d(ContentValues.TAG, "onCreate: gagal,${it.message}")
            })
    }

}
