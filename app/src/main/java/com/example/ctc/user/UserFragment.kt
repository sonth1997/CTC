package com.example.ctc.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.example.ctc.R
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment(){

//    private var googleApiClient: GoogleApiClient? = null
//    private var gso: GoogleSignInOptions? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user,container,false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()
//
//        googleApiClient = context?.let {
//            val build = GoogleApiClient.Builder(it)
//                .enableAutoManage(context as FragmentActivity, this)
//                .addApi<GoogleSignInOptions?>(Auth.GOOGLE_SIGN_IN_API, gso!!)
//                .build()
//            build
//        }
//
//        imgLogout.setOnClickListener {
//            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback { status ->
//                if (status.isSuccess) {
//                    gotoLoginFragment()
//                } else {
//                    Toast.makeText(context,
//                        "Session not close",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//        }
//    }
//    override fun onStart() {
//        super.onStart()
//        val opr =
//            Auth.GoogleSignInApi.silentSignIn(googleApiClient)
//        if (opr.isDone) {
//            val result = opr.get()
//            handleSignInResult(result)
//        } else {
//            opr.setResultCallback { googleSignInResult -> handleSignInResult(googleSignInResult) }
//        }
//    }
//    private fun handleSignInResult(result: GoogleSignInResult) {
//        if (result.isSuccess) {
//            val account = result.signInAccount
//            tvNameUser.text = account!!.displayName
//            tvNameEmail.text = account.email
//            try {
//                Glide.with(this).load(account.photoUrl).into(imgEmail)
//            } catch (e: NullPointerException) {
//                Toast.makeText(context, "image not found", Toast.LENGTH_LONG).show()
//            }
//        } else {
//            gotoLoginFragment()
//        }
//    }
//    private fun gotoLoginFragment() {
//       val bundle : Bundle
//
//    }
//    override fun onConnectionFailed(p0: ConnectionResult) {
//    }
}
