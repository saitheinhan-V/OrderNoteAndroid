package com.note.order.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.mukesh.OnOtpCompletionListener
import com.note.order.MainActivity
import com.note.order.R
import com.note.order.utils.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_otp.*

class OtpFragment : Fragment() {

    private var otpCompleted = false
    private var otpCode = ""
    private var verificationCode = ""

    // get reference of the firebase auth
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_otp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        //code from Messaging
        verificationCode = arguments?.getString("otp","").toString()

        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        otpView.setOtpCompletionListener(OnOtpCompletionListener {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            otpCode = it
        })

        btnConfirm.setSafeOnClickListener {
            if(otpCode.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Please input verification code", Toast.LENGTH_SHORT).show()
                return@setSafeOnClickListener
            }
            if(verificationCode.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Verification code is empty", Toast.LENGTH_SHORT).show()
                return@setSafeOnClickListener
            }
            if(otpCode.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    verificationCode, otpCode)
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(requireContext(), "Verify failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // verifies if the code matches sent by firebase
    // if success start the new activity in our case it is main Activity
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {

                    Toast.makeText(requireContext(), "Verify completed", Toast.LENGTH_SHORT).show()

                    val intent = Intent(requireContext() , MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(requireContext(),"Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}