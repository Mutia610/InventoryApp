package com.example.inventoryapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.inventoryapp.R
import com.example.inventoryapp.local.DatabaseStocks
import com.example.inventoryapp.viewModel.ViewModelUser
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_register1.*
import kotlinx.android.synthetic.main.fragment_register2.*
import kotlinx.android.synthetic.main.fragment_register2.back
import kotlinx.android.synthetic.main.fragment_register2.textEmail

class Register2Fragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    private var databaseStock: DatabaseStocks? = null
    private lateinit var viewModelUser: ViewModelUser

    var getNameStore : String? = null
    var getEmail : String? = null
    var getPassword: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register2, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseStock = context?.let { DatabaseStocks.getInstance(it) }
        viewModelUser = ViewModelProvider(this).get(ViewModelUser::class.java)

        getNameStore = arguments?.getString("name")
        getEmail = arguments?.getString("email")
        getPassword = arguments?.getString("password")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btnFinish.setOnClickListener(this)
        back.setOnClickListener(this)
        textHalo.text = "Halo $getNameStore, silahkan isi password dibawah ini untuk menyelesaikan proses registrasi"
        textEmail.text = getEmail

        attachObserve()
    }

    private fun attachObserve() {
        viewModelUser._responseActionUser.observe(viewLifecycleOwner, Observer { successRegister() })
        viewModelUser._isErrorUser.observe(viewLifecycleOwner, Observer { errorRegister(it) })
        viewModelUser._password_empty.observe(viewLifecycleOwner, Observer { emptyPassword() })
        viewModelUser._password_less.observe(viewLifecycleOwner, Observer { lessPassword() })
        viewModelUser._passwordnotmatch.observe(viewLifecycleOwner, Observer { passNotMatch() })

    }

    private fun passNotMatch() {
        textConfirmationPass.error="Confirmation Password Tidak Sesuai"
    }

    private fun lessPassword() {
        textPassword.error="Password kurang dari 8 karakter"
    }

    private fun emptyPassword() {
        textPassword.error="Password harus diisi"
    }

    private fun successRegister() {

        val bundle = bundleOf(
            "password" to textPassword.text.toString(),
            "email" to textEmail.text.toString(),
            "name" to getNameStore
        )
        navController.navigate(R.id.action_register2Fragment2_to_resultFragment3, bundle)
        clearFindViewByIdCache()
    }

    private fun errorRegister(it :Throwable) {
        Toast.makeText(context, "Register gagal", Toast.LENGTH_SHORT).show()
        Log.d("TAG", "errorRegister: ${it.message}")
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnFinish ->  viewModelUser.registerUser(
                null,
                getNameStore.toString(),
                getEmail.toString(),
                textPassword.text.toString(),
                textConfirmationPass.text.toString()
            )
            R.id.back -> navController.navigate(R.id.register1Fragment2)
        }

    }

    /*override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btnFinish ->
            {
                if (textPassword.text.toString().isEmpty()){
                    textPassword.error = "Password harus diisi"
                }
                else if(textConfirmationPass.text.toString().isEmpty())
                    textConfirmationPass.error = "Confirmation password harus diisi"
                else if (textPassword.text.toString().length < 8)
                    textPassword.error = "Password kurang dari 8 karakter"
                else
                    if(textConfirmationPass.text.toString().equals(textPassword.text.toString())){
                        val bundle = bundleOf("nameStore" to getNameStore, "email" to getEmail)
                        navController.navigate(R.id.action_register2Fragment2_to_resultFragment3, bundle)}
                    else
                        Toast.makeText(context,"Password tidak sama degan Confirmation Password", Toast.LENGTH_LONG).show()
            }
            R.id.back -> activity?.onBackPressed()
        }
    }*/
}