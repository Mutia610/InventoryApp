package com.example.inventoryapp.viewModel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inventoryapp.local.User
import com.example.inventoryapp.repo.UserRepository

class ViewModelUser(application: Application):AndroidViewModel(application) {

    val repositoryUser = UserRepository(application.applicationContext)
    var responseDataUser = MutableLiveData<List<User>>()
    var _responseDataUser: LiveData<List<User>> = responseDataUser

    var responseActionUser = MutableLiveData<User>()
    var _responseActionUser: LiveData<User> = responseActionUser

    var isErrorUser = MutableLiveData<Throwable>()
    var _isErrorUser: LiveData<Throwable> = isErrorUser

    var password_empty = MutableLiveData<Boolean>()
    var _password_empty: LiveData<Boolean> = password_empty

    var password_notmatch = MutableLiveData<Boolean>()
    var _passwordnotmatch: LiveData<Boolean> = password_notmatch

    var password_less = MutableLiveData<Boolean>()
    var _password_less: LiveData<Boolean> = password_less

    var empty_email = MutableLiveData<Boolean>()
    var _empty_email: LiveData<Boolean> = empty_email

    var empty_name = MutableLiveData<Boolean>()
    var _empty_name: LiveData<Boolean> = empty_name

    var wrong_email = MutableLiveData<Boolean>()
    var _wrong_email: LiveData<Boolean> = wrong_email


    fun getEmail(email: String, name: String) {
        if  (name.isEmpty()) {
            empty_name.value = true
        } else if (email.isEmpty()) {
            empty_email.value = true
        } else if (validasiEmail(email) == false) {
            wrong_email.value = true
        } else {
            repositoryUser.checkEmail(email, {
                responseActionUser.value = it
            }, {
                isErrorUser.value = it
            })
        }
    }


    fun loginUser(email: String, password: String) {
        if(email.isEmpty()){
            empty_email.value=true
        }else if(password.isEmpty()){
            password_empty.value=true
        }else {
            repositoryUser.loginUser(email, password, {
                responseActionUser.value = it

            }, {
                isErrorUser.value = it

            })
        }
    }

    fun registerUser(
        id: Int?,
        nama: String,
        email: String,
        password: String,
        passwordKonf: String
    ) {
        if (password.isEmpty()) {
            password_empty.value = true
        } else if (password != passwordKonf) {
            password_notmatch.value = true
        } else if (password.length < 8) {
            password_less.value = true
        } else {
            repositoryUser.registerUser(id, nama, email, password, passwordKonf, {
                responseActionUser.value = it
            }, {
                isErrorUser.value = it

            })
        }
    }

    private fun validasiEmail(email: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true
        } else {
            return false
        }
    }
}