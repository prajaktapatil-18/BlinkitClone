package com.example.blinkitclone.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.blinkitclone.Utils
import com.example.blinkitclone.models.Users
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit

class AuthViewModel: ViewModel() {


    private val _isSignInSuccessfully = MutableStateFlow(false)
    val isSignInSuccessfully = _isSignInSuccessfully

    // verification id   mutablestateflow   work same as livedata
    private val _verificationId = MutableStateFlow<String?>(null)


    // sending otp check sent or not

    private val _otpsent = MutableStateFlow(false)
    var opt = _otpsent


    // to check if current user is preasent so directly navigate on home frag

    private val _isCurrentUser = MutableStateFlow(false)
    val isCurrentUser = _isCurrentUser


    fun sendOTP(usernumber: String, activity: Activity) {

        // callback code

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {

                _verificationId.value = verificationId
                opt.value = true
            }
        }

            // verification code from firebase
            val options = PhoneAuthOptions.newBuilder(Utils.getFirebaseInstance())
                .setPhoneNumber("+91$usernumber") // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activity) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)

        }
    }

    fun signInWithPhoneAuthCredential(otp: String, usernumber: String, user: Users) {
        val credential = PhoneAuthProvider.getCredential()
        Utils.getFirebaseInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    FirebaseDatabase.getInstance().getReference("AllUsers").child("Users")
                        .child(user.uid!!).setValue(user)
                //

                    val user = task.result?.user
                } else {


                }
            }
    }
