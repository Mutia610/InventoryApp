package com.example.inventoryapp.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inventoryapp.local.Stocks
import com.example.inventoryapp.repo.StockRepository

class ViewModelStock(application: Application) : AndroidViewModel(application) {

    val context : Context = application
    val repository = StockRepository(application.applicationContext)

    var responseData = MutableLiveData<List<Stocks>>()
    var responsesData: LiveData<List<Stocks>> = responseData

    var responseAksi = MutableLiveData<Stocks>()
    var responsesAksi: LiveData<Stocks> = responseAksi

    var isError = MutableLiveData<Throwable>()
    var error: LiveData<Throwable> = isError

    var empty_nama = MutableLiveData<Boolean>()
    var empty_namab: LiveData<Boolean> = empty_nama

    var empty_jumlah = MutableLiveData<Boolean>()
    var empty_jml: LiveData<Boolean> = empty_jumlah

    var empty_keterangan = MutableLiveData<Boolean>()
    var empty_ket: LiveData<Boolean> = empty_keterangan


    fun getListStock(){
        repository.getStock({
            responseData.value = it
        },{
            isError.value = it
        })
    }

    fun insertStock(id:Int?, date:String?, nama_barang:String?, jumlah_stock:String?, keterangan:String?){
        repository.insert(id, date, nama_barang, jumlah_stock, keterangan, {
            responseAksi.value = it
//            Toast.makeText(context, "Insert Data Berhasil", Toast.LENGTH_LONG).show()

            Log.d("TAG", "insertStock: $id, $date, $nama_barang, $jumlah_stock, $keterangan")
        }, {
            isError.value = it
            Log.d("TAG", "insertStock: $id, $date, $nama_barang, $jumlah_stock, $keterangan")
        })
    }

    fun updateStock(id:Int?, date:String?, nama_barang:String?, jumlah_stock:String?, keterangan:String?){
        repository.update(id, date, nama_barang, jumlah_stock, keterangan, {
            responseAksi.value = it
     //       Toast.makeText(context, "Update Data Berhasil", Toast.LENGTH_LONG).show()
        },{
            isError.value = it
        })
    }

    fun deleteStock(item: Stocks){
        repository.delete(item,{
            responseAksi.value = it
     //       Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
        },{
            isError.value = it
        })
    }
}