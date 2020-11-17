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
import com.example.inventoryapp.viewModel.ViewModelUser
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_register1.*


class Register1Fragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    private lateinit var userViewModel: ViewModelUser


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register1, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        btnNext.setOnClickListener(this)
        back.setOnClickListener(this)

        attachObserve()
    }

    private fun attachObserve() {
        userViewModel._responseActionUser.observe(viewLifecycleOwner, Observer { getUser() })
        userViewModel._isErrorUser.observe(viewLifecycleOwner, Observer { emptyUser(it) })
        userViewModel.wrong_email.observe(viewLifecycleOwner, Observer { wrongEmail() })
        userViewModel.empty_email.observe(viewLifecycleOwner, Observer { emptyEmail() })
        userViewModel.empty_name.observe(viewLifecycleOwner, Observer { emptyName() })

    }

    private fun emptyName() {
        textStoreName.error="Nama tidak boleh kosong"
    }

    private fun emptyEmail() {
        textEmail.error="Email tidak boleh kosong"
    }


    private fun wrongEmail() {
        textEmail.error="Email tidak valid"
    }

    private fun emptyUser(it: Throwable?) {
        Log.d("TAG", "Lanjut register, email belum ada")
        val bundle = bundleOf(
            "name" to textStoreName.text.toString(),
            "email" to textEmail.text.toString()
        )
        navController.navigate(
            R.id.action_register1Fragment2_to_register2Fragment2,
            bundle
        )
        clearFindViewByIdCache()
    }

    private fun getUser() {
        Log.d("TAG", "email sudah terdaftar")
        Toast.makeText(context, "Email sudah terdaftar", Toast.LENGTH_SHORT).show()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNext ->{
                userViewModel.getEmail(textEmail.text.toString(),textStoreName.text.toString())
            }
            //R.id.back -> activity?.onBackPressed()
            R.id.back -> navController.navigate(R.id.mainFragment)

        }
    }

 /*   override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnNext -> {
                if (textStoreName.text.toString().isEmpty()) {
                    textStoreName.error = "Name Store ini harus diisi"
                } else if (textEmail.text.toString().isEmpty()) {
                    textEmail.error = "Email harus diisi"
                } else {
                    val bundle = bundleOf(
                        "nameStore" to textStoreName.text.toString(),
                        "email" to textEmail.text.toString()
                    )
                    navController.navigate(R.id.action_register1Fragment2_to_register2Fragment2, bundle)
                }
            }
            R.id.back -> activity?.onBackPressed()
        }
    }*/
}