package com.note.order.ui.account

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.note.order.MainActivity
import com.note.order.R
import com.note.order.request.LoginRequest
import com.note.order.response.AccountResponse
import com.note.order.utils.isNumber
import com.note.order.utils.setSafeOnClickListener
import com.note.order.viewModels.AccountViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.*


class LoginFragment : Fragment() {

    private lateinit var phone: String
    private lateinit var password: String
    private var isPhone = false
    private var isPassword = false

    private val accountViewModel: AccountViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvSkip.setOnClickListener {
            startActivity(Intent(requireContext(),MainActivity::class.java))
            activity?.finish()
        }

        btnLogin.setSafeOnClickListener {
            if(isPhone && isPassword){
                val req = LoginRequest("1",phone,"John","20")

                loginAPICall(req)

//                accountViewModel.login(req).observe(viewLifecycleOwner, Observer {
//                    when(it.status){
//                        Resource.Status.LOADING -> {
//
//                        }
//                        Resource.Status.ERROR -> {
//                            Toast.makeText(requireContext(), "Request got error!", Toast.LENGTH_LONG).show()
//                        }
//                        Resource.Status.SUCCESS -> {
//                            Log.i("login",it.data.toString())
//                            Toast.makeText(requireContext(), "Login Success!", Toast.LENGTH_LONG).show()
//                            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
//                        }
//                    }
//                })


//                if(response.toString().isNotEmpty()) {
//
//                    if (response.toString() == "No user found")
//                        Toast.makeText(requireContext(), "Login invalid!", Toast.LENGTH_LONG).show()
//                    else {
//                        Toast.makeText(requireContext(), "Login Success!", Toast.LENGTH_LONG).show()
//                        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
//                    }
//                }else{
//                    Toast.makeText(requireContext(), "Request got error!", Toast.LENGTH_LONG).show()
//                }

            }else{

                Toast.makeText(requireContext(),"Invalid information!",Toast.LENGTH_LONG).show()
            }
        }

        registerTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        forgotPasswordTextView.setOnClickListener {

        }

        edtPhone.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                phone = s.toString().trim()
                if (phone.isNotEmpty()) {
                    if(isNumber(phone)){ // is number
                        if (phone.startsWith("09") && phone.length >= 9 && phone.length <= 11) {
                            phoneTxtInputLayout.isErrorEnabled = false
                            isPhone = true
                        } else {
                            phoneTxtInputLayout.error = "**Please input correct phone number"
                            phoneTxtInputLayout.isErrorEnabled = true
                            isPhone = false
                        }
                    }
                    else{ // is text
//                        if (!s.isValidEmail())
//                            emailOrPhoneNoTIL.error = getString(R.string.email_check)
//                        else
//                            emailOrPhoneNoTIL.isErrorEnabled = false
                    }
                }else{
                    isPhone = false
                    phoneTxtInputLayout.error = "**Please input phone number"
                    phoneTxtInputLayout.isErrorEnabled = true
                }
            }

        })

        edtPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                password = s.toString().trim()
                if(password.isNotEmpty()){
                    passwordTxtInputLayout.isErrorEnabled = false
                    isPassword = true
                }else{
                    passwordTxtInputLayout.isErrorEnabled = true
                    passwordTxtInputLayout.error = "**Please input password"
                    isPassword = false
                }
            }

        })
    }

    private fun loginAPICall(req: LoginRequest) {

        GlobalScope.launch {
            val response = accountViewModel.login(req)!!
            checkUserLogin(response)
        }
        runBlocking {
            delay(4000L)
        }
    }

    private fun checkUserLogin(response: AccountResponse){
        if(response == AccountResponse()){
            Toast.makeText(requireContext(),"empty data",Toast.LENGTH_LONG).show()
            return
        }
        Log.i("logged",response.toString())
        Toast.makeText(requireContext(),response.toString(),Toast.LENGTH_LONG).show()
    }
}