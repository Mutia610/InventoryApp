package com.example.inventoryapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.inventoryapp.R
import com.example.inventoryapp.local.DatabaseStocks
import com.example.inventoryapp.viewModel.ViewModelUser
import kotlinx.android.synthetic.main.fragment_register2.back
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : Fragment(), View.OnClickListener{

    lateinit var navController: NavController

    var getNameStore: String? = null
    var getEmail: String? = null
    var getPassword: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getEmail = arguments?.getString("email")
        getNameStore = arguments?.getString("name")
        getPassword = arguments?.getString("password")

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        back.setOnClickListener(this)
        btnBackLogin.setOnClickListener(this)
        tvNameStore.text = getNameStore
        tvEmail.text = getEmail
        Toast.makeText(context, "Data telah disimpan", Toast.LENGTH_LONG).show()
    }
    override fun onClick(v: View?) {
        when(v?.id){
            //R.id.back -> activity?.onBackPressed()
            R.id.back -> navController.navigate(R.id.register2Fragment2)
            R.id.btnBackLogin -> navController.navigate(R.id.action_resultFragment3_to_mainFragment)
        }
    }
}