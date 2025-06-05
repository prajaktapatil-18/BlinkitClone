package com.example.blinkitclone

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.blinkitclone.activity.UserActivity
import com.example.blinkitclone.databinding.FragmentSplashBinding
import com.example.blinkitclone.viewmodel.AuthViewModel
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private val viewmodel : AuthViewModel by viewModels()

    private  lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentSplashBinding.inflate(layoutInflater)

// for if user already have login so redirect to home fragment

        lifecycleScope.launch {
            viewmodel.isCurrentUser.collect{
                 if (it){
                     startActivity(Intent(requireActivity(),UserActivity::class.java))
                     requireActivity().finish()
                 } else{
                     findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
                 }
            }
        }





        Handler(Looper.getMainLooper()).postDelayed({
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.splashFragment, true) // This line ensures the splash screen is removed from the back stack
                .build()
            findNavController().navigate(R.id.action_splashFragment_to_signInFragment, null, navOptions)
        }, 2000)
        return binding.root

    }

    private fun setStatusBarColor(){
        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(),R.color.yellow)
            statusBarColor =statusBarColors

            if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }

        }
    }


}