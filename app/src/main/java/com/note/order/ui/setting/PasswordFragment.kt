package com.note.order.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hanks.passcodeview.PasscodeView
import com.note.order.R
import com.note.order.utils.AppSharedPreference
import kotlinx.android.synthetic.main.fragment_password.*

class PasswordFragment : Fragment() {

    private lateinit var sharedPreference: AppSharedPreference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreference = AppSharedPreference(requireContext())

        passcodeView.setPasscodeLength(4)
            .listener = object : PasscodeView.PasscodeViewListener {
            override fun onFail() {
                // to show message when Password is incorrect
                Toast.makeText(requireContext(), "Password is wrong!", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onSuccess(number: String) {
                // here is used so that when password
                // is correct user will be
                // directly navigated to next activity
                sharedPreference.savePassCode("pass_code", number)
                Toast.makeText(requireContext(), "Passcode successfully set!", Toast.LENGTH_SHORT)
                    .show()
                activity?.finish()

            }
        }
    }
}