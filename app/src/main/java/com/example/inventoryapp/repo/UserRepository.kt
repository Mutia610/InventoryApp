package com.example.inventoryapp.repo

import android.content.Context
import com.example.inventoryapp.helper.SessionManager
import com.example.inventoryapp.local.DatabaseStocks
import com.example.inventoryapp.local.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class UserRepository(val context: Context) {

    private var stockDatabase = DatabaseStocks.getInstance(context)
    val session = SessionManager(context)


    fun registerUser(id:Int?,name:String,email:String,password:String,passwordKonfirmasi:String,responseHandler:(User)->Unit,errorHandler:(Throwable)->Unit) {

        Observable.fromCallable { stockDatabase?.userDao()?.register(User(id,name,email, password))}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ item ->
                responseHandler(User(id,name,email,password))
            }, {
                errorHandler(it)
            })
    }

    fun loginUser(email:String, password:String, responseHandler: (User) -> Unit, errorHandler: (Throwable) -> Unit){

        Observable.fromCallable { stockDatabase?.userDao()?.login(email,password)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({item->
                if (item != null) {
                    responseHandler(item)
                }
                session.nama=item?.user_name
                session.login = true
            },{
                errorHandler(it)
            })
    }

    fun checkEmail(email: String, responseHandler: (User) -> Unit, errorHandler: (Throwable) -> Unit){
        Observable.fromCallable { stockDatabase?.userDao()?.getUser(email) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({item->
                if (item != null) {
                    responseHandler(item)
                }
            },{
                errorHandler(it)
            })
    }

}