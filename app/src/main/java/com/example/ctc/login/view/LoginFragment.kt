package com.example.ctc.login.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.ctc.R
import com.example.ctc.activity.MainActivity
import com.example.ctc.api.RetrofitApi
import com.example.ctc.login.model.LoginRequest
import com.example.ctc.pref.AppPrefs
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*
import kotlin.collections.ArrayList


class LoginFragment : Fragment(), GoogleApiClient.OnConnectionFailedListener {

    private var callbackManager: CallbackManager? = null
    private var facebookCallback : FacebookCallback<LoginResult> ?= null
    private var mLoginMgr: LoginManager? = null
    private val mPermissions: ArrayList<String> = arrayListOf("email")

    companion object {
        const val REQUEST_LOGIN = "LOGIN.REQUEST"
    }

    private var googleApiClient: GoogleApiClient? = null
    private val RC_SIGN_IN = 1

    private val api = RetrofitApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        btnLoginFacebook.setPermissions("email")
//        btnLoginFacebook.fragment = this

        btnLoginGmail.setOnClickListener {
            loginGmail()
        }
        btnLoginFacebook.setOnClickListener {
            loginFacebook()
        }
    }
    private fun loginFacebook(){
        LoginManager.getInstance().logOut()

        callbackManager = CallbackManager.Factory.create()
        mLoginMgr = LoginManager.getInstance()
        mLoginMgr?.registerCallback(callbackManager, facebookCallback)
        mLoginMgr?.logInWithReadPermissions(this, mPermissions)
        facebookCallback = object:FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
//                gotoHome()
                val request = LoginRequest(
                    "2019-1-1",
                    loginResult.accessToken?.expires?.toString() ?: "",
                    loginResult.accessToken?.declinedPermissions?.toString() ?: ""
                )
                request.avatar = "https://i.imgur.com/ygAly9D.jpg"
                api.login(REQUEST_LOGIN, request,
                    success = { _, reponse ->
                        val name = reponse.data
                        AppPrefs.getInstance().name = name
                        gotoHome()
                    }, error = { _, e ->

                    })
            }
            override fun onCancel() {
                Toast.makeText(context, "Loi message: ", Toast.LENGTH_SHORT).show()
            }

            override fun onError(e: FacebookException) {
                Toast.makeText(context, "Loi message: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun loginGmail() {
        val gso = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        )
            .requestEmail()
            .build()
        googleApiClient = context?.let {
            GoogleApiClient.Builder(it)
                .enableAutoManage(context as FragmentActivity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
        }

        btnLoginGmail.setOnClickListener {
            val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
            startActivityForResult(intent, RC_SIGN_IN)
        }
    }

    private fun gotoHome() {
        startActivity(Intent(context, MainActivity::class.java))
        activity?.finish()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }

    }
    private fun handleSignInResult(result: GoogleSignInResult) {
        val request = LoginRequest(
            "2019-1-1",
            result.signInAccount?.photoUrl?.toString() ?: "",
            result.signInAccount?.displayName ?: ""
        )
        request.avatar ="https://i.imgur.com/ygAly9D.jpg"
        api.login(REQUEST_LOGIN, request, success = { _, apiResponse ->
            if (result.isSuccess) {
                val name = apiResponse.data
                AppPrefs.getInstance().name = name
                gotoHome()
            } else {
                Toast.makeText(context, "Loi code: ${apiResponse.code} ", Toast.LENGTH_SHORT).show()
            }
        }, error = { _, e ->
            Toast.makeText(context, "Loi message: ${e.message}", Toast.LENGTH_SHORT).show()
        })
    }
}