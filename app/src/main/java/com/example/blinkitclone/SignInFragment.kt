package com.example.blinkitclone

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.blinkitclone.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater)
        getUserNumber()
        btnContinue()
        return binding.root

    }

    private fun btnContinue() {
        binding.btnContinue.setOnClickListener {
            val number = binding.editTvuserNum.text.toString()

            if (number.isEmpty() || number.length != 10) {
                Utils.showToast(requireContext(), "check phone number again")
            }else{
                val bundle = Bundle()
                bundle.putString("number",number)
                findNavController().navigate(R.id.action_signInFragment_to_OTPFragment,bundle)

            }
        }
    }

    private fun getUserNumber() {
        binding.btnContinue.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(number: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    val len = number?.length
                    if (len == 10) {
                        binding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                    } else {
                        binding.btnContinue.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.gray
                            )
                        )
                    }

                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
    }

}




