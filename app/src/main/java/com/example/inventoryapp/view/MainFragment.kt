package com.example.inventoryapp.view

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.inventoryapp.R
import com.example.inventoryapp.local.User
import com.example.inventoryapp.viewModel.ViewModelStock
import com.example.inventoryapp.viewModel.ViewModelUser
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(),View.OnClickListener {

    lateinit var navController: NavController
    private lateinit var viewModelUser: ViewModelUser

    var get_email: String? = null
    var get_password: String? = null
    var get_name:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btnRegister.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
        showPassword.setOnClickListener(this)
        attachObserve()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelUser = ViewModelProvider(this).get(ViewModelUser::class.java)
        get_email = arguments?.getString("email")
        get_password = arguments?.getString("password")
        get_name = arguments?.getString("name")
    }

    private fun attachObserve() {
        viewModelUser._responseActionUser.observe(viewLifecycleOwner, Observer { loginSuccess(it) })
        viewModelUser._isErrorUser.observe(viewLifecycleOwner, Observer { errorLogin(it) })
        viewModelUser._empty_email.observe(viewLifecycleOwner, Observer { emptyEmail() })
        viewModelUser._password_empty.observe(viewLifecycleOwner, Observer { emptyPassword() })

    }

    private fun loginSuccess(it:User) {
        Log.d("TAG", "loginSuccess: OK")
        val bundle= bundleOf(
            "password" to textPassword.text.toString(),
            "email" to textLEmail.text.toString()
        )
        navController.navigate(R.id.action_mainFragment_to_homeActivity,bundle)
        activity?.finish()
    }

    private fun errorLogin(it:Throwable) {
        Toast.makeText(context,"Login Failed, Email Belum Terdaftar atau Password Salah", Toast.LENGTH_SHORT).show()
        Log.d("TAG", "errorLogin: ${it.message}")
    }

    private fun emptyEmail() {
        textLEmail.error="Email harus diisi"
    }

    private fun emptyPassword() {
        textPassword.error="Password harus diisi"
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.btnRegister -> navController.navigate(R.id.action_mainFragment_to_register1Fragment2)
            R.id.btnLogin -> viewModelUser.loginUser(textLEmail.text.toString(),textPassword.text.toString())
            //navController.navigate(R.id.action_mainFragment_to_homeActivity)
            R.id.showPassword -> {
                if (showPassword.isChecked) {
                    textPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                } else {
                    textPassword.setTransformationMethod(PasswordTransformationMethod.getInstance())
                }
            }
        }
    }
}