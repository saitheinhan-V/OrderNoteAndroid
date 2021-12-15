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
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.note.order.R
import com.note.order.utils.isNumber
import com.note.order.utils.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.edtPassword
import kotlinx.android.synthetic.main.fragment_register.edtPhone
import kotlinx.android.synthetic.main.fragment_register.ivBack
import kotlinx.android.synthetic.main.fragment_register.phoneTxtInputLayout
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.note.order.MainActivity

import com.google.firebase.FirebaseException
import com.google.firebase.auth.*

import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import java.util.concurrent.TimeUnit


class RegisterFragment : Fragment() {

    private var phone: String = ""
    private var password: String = ""
    private var confirmPassword: String = ""
    private var isPhone = false
    private var isPassword = false
    private var isConfirm = false
    private var verificationCode = ""

    private lateinit var auth: FirebaseAuth
    private lateinit var mCallback: OnVerificationStateChangedCallbacks

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        auth.useAppLanguage()

        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        mCallback = object : OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                Toast.makeText(requireContext(), "verification completed", Toast.LENGTH_SHORT)
                    .show()

                startActivity(Intent(requireContext(),MainActivity::class.java))
                activity?.finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("tag", "onVerificationFailed: $e")
                Toast.makeText(requireContext(), "$e", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                verificationCode = s
                Log.d("tag", "onCodeSent: $verificationCode")
                Toast.makeText(requireContext(), "Code sent", Toast.LENGTH_SHORT).show()

                if(verificationCode.isNotEmpty()){
                    findNavController().navigate(
                        R.id.action_registerFragment_to_otpFragment,
                        bundleOf( "otp" to verificationCode)
                    )
                }else{
                    Toast.makeText(requireContext(), "Failed to send verification code", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnRegister.setSafeOnClickListener {
            if(isPhone && isPassword && isConfirm){
                if(confirmPassword == password) {

                    val ph = "+95"+ phone.substring(1,phone.length)
                    Log.i("ph", "onViewCreated: $ph")

                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(ph)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallback)          // OnVerificationStateChangedCallbacks
                        .build()

                    PhoneAuthProvider.verifyPhoneNumber(options)

                }else {
                    Toast.makeText(requireContext(), "Please check password", Toast.LENGTH_SHORT).show()
                    confirmPasswordTxtInputLayout.error = "**Please check confirm password"
                    passwordInputLayout.error = "**Please check password"
                    confirmPasswordTxtInputLayout.isErrorEnabled = true
                    passwordInputLayout.isErrorEnabled = true
                }
            }else{
                Toast.makeText(requireContext(),"Invalid information!", Toast.LENGTH_LONG).show()
            }
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
                if (password.isNotEmpty()) {
                    isPassword = true
                    passwordInputLayout.isErrorEnabled = false
                } else {
                    isPassword = false
                    passwordInputLayout.isErrorEnabled = true
                    passwordInputLayout.error = "**Please input password"
                }
            }
        })

        edtConfirmPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                confirmPassword = s.toString().trim()
                if (confirmPassword.isNotEmpty()) {
                    if(confirmPassword == password){
                        isConfirm = true
                        confirmPasswordTxtInputLayout.isErrorEnabled = false
                    }else{
                        isConfirm = false
                        confirmPasswordTxtInputLayout.isErrorEnabled = true
                        confirmPasswordTxtInputLayout.error = "**Please check password"
                    }
                } else {
                    isConfirm = false
                    confirmPasswordTxtInputLayout.isErrorEnabled = true
                    confirmPasswordTxtInputLayout.error = "**Please input confirm password"
                }
            }

        })


    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                   // Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    //Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
}