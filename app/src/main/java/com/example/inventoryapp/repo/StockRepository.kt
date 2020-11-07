package com.example.inventoryapp.repo

import android.content.Context
import com.example.inventoryapp.local.DatabaseStocks
import com.example.inventoryapp.local.Stocks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class StockRepository(val context: Context) {

    private var stockDatabase = DatabaseStocks.getInstance(context)

    fun getStock(responHandler: (List<Stocks>) -> Unit, errorHandler: (Throwable)-> Unit) {
        Observable.fromCallable { stockDatabase?.stocksDao()?.getAll()}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ item ->
                item?.let { responHandler(it) }
            },{
                errorHandler(it)
            })
    }

    fun insert(id:Int?, date:String?, nama_barang:String?, jumlah_stock:String?, keterangan:String?, responHandler: (item:Stocks) -> Unit, errorHandler: (Throwable)-> Unit){
        Observable.fromCallable {
            stockDatabase?.stocksDao()?.insert(Stocks(id, date, nama_barang, jumlah_stock, keterangan))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
               responHandler(Stocks(id, date, nama_barang, jumlah_stock, keterangan))
            },{
                errorHandler(it)
            })
    }

    fun update(id:Int?, date:String?, nama_barang:String?, jumlah_stock:String?, keterangan:String?, responHandler: (item:Stocks) -> Unit, errorHandler: (Throwable)-> Unit){
        Observable.fromCallable {
            stockDatabase?.stocksDao()?.update(Stocks(id, date, nama_barang, jumlah_stock, keterangan)) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responHandler(Stocks(id, date, nama_barang, jumlah_stock, keterangan))
            },{
                errorHandler(it)
            })
    }

    fun delete(item: Stocks?, responseHandler: (item : Stocks) -> Unit, errorHandler: (Throwable)-> Unit){
        Observable.fromCallable { stockDatabase?.stocksDao()?.delete(item) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (item != null) {
                    responseHandler(item)
                }
            },{
                errorHandler(it)
            })
    }
}