package com.example.blinkitclone

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.blinkitclone.activity.UserActivity
import com.example.blinkitclone.databinding.FragmentOTPBinding
import com.example.blinkitclone.models.Users
import com.example.blinkitclone.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class OTPFragment : Fragment() {
    private val viewmodel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentOTPBinding
    lateinit var userNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOTPBinding.inflate(layoutInflater)

        getUserNumber()
        customisingEnteringOTP()
        sendOTP()
        onLoginButtonClick()
          backButtonPressed()

        return binding.root
    }

    private fun onLoginButtonClick() {

        binding.btnLogin.setOnClickListener {
            Utils.showDialog(requireContext(), "signing you")
            val editText = arrayOf(
                binding.otp1,
                binding.otp2,
                binding.otp3,
                binding.otp4,
                binding.otp5,
                binding.otp6
            )
            val otp = editText.joinToString("") { it.text.toString() }

            if (otp.length < editText.size) {
                Utils.showToast(requireContext(), "please enter valid otp")
            } else {
                editText.forEach { it.text?.clear(); it.clearFocus() }
            }
            verifyOTP(otp)

        }

    }

    private fun verifyOTP(otp: String) {
        val user =
            Users(uid = Utils.getCurrentUserId(), userPhoneNumber = userNumber, userAddress = null)
        viewmodel.signInWithPhoneAuthCredential(otp, userNumber, user)

        lifecycleScope.launch {
            viewmodel.isSignInSuccessfully.collect {
                if (it) {
                    Utils.hideDialog()
                    Utils.showToast(requireContext(), "loggeed in..")
                    startActivity(Intent(requireActivity(), UserActivity::class.java))
                    requireActivity().finish()
                }
            }
        }

    }


    private fun sendOTP() {
        Utils.showDialog(requireContext(), "sending otp")
        viewmodel.apply {
            sendOTP(userNumber, requireActivity())

            /// lifecyclescope is coroutine
            lifecycleScope.launch {

                opt.collect {

                    if (it) {
                        Utils.hideDialog()
                        Utils.showToast(requireContext(), "otp sent")

                    }
                }
            }

        }


    }


    private fun customisingEnteringOTP() {
        val editText = arrayOf(
            binding.otp1,
            binding.otp2,
            binding.otp3,
            binding.otp4,
            binding.otp5,
            binding.otp6
        )
        for (i in editText.indices) {
            editText[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(input: Editable?) {
                    // here input means that edittext
                    if (input?.length == 1) {
                        if (i < editText.size - 1) {
                            editText[i + 1].requestFocus()
                        }
                    } else if (input?.length == 0) {
                        if (i > 0) {
                            editText[i - 1].requestFocus()
                        }


                    }
                }

            })
        }
    }


    private fun backButtonPressed() {

        binding.toolbarOtp.setNavigationOnClickListener {
//             val navOptions = NavOptions.Builder()
//                 .setPopUpTo(R.id.signInFragment, true) // This line ensures the sign in screen is removed from the back stack
//                 .build()
            findNavController().navigate(R.id.action_OTPFragment_to_signInFragment)
        }

    }


    private fun getUserNumber() {
        val bundle = arguments
        userNumber = bundle?.getString("number").toString()

        binding.gettingUserNumber.text = userNumber


    }

}