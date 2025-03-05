package com.example.blinkitclone

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.example.blinkitclone.databinding.ProgreesDialogBinding
import com.google.firebase.auth.FirebaseAuth

object Utils {

 private  var dialog :AlertDialog? = null
    fun showToast(context: Context, message :String){

       Toast.makeText(context,message,Toast.LENGTH_SHORT).show()

    }

    fun showDialog(context: Context,message: String){
        val progress = ProgreesDialogBinding.inflate(LayoutInflater.from(context))
        progress.tvPbMessage.text= message
        dialog = AlertDialog.Builder(context).setView(progress.root).setCancelable(false).create()
        dialog!!.show()

    }

    fun hideDialog(){
        dialog?.dismiss()
    }

 private var firebseAuthInstance:FirebaseAuth? =null
    fun getFirebaseInstance():FirebaseAuth{
         if (firebseAuthInstance == null){
             firebseAuthInstance = FirebaseAuth.getInstance()

         }
        return firebseAuthInstance!!

    }
    fun getCurrentUserId():String{
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

}