package com.psquaredllc.smack.controller

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.psquaredllc.smack.R
import com.psquaredllc.smack.utilities.BROADCAST_USER_DATA_CHANGE
import com.psquaredllc.smack.services.AuthService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginSpinner.visibility = View.INVISIBLE
    }

    fun loginLoginBtnClicked(view: View){
        enableSpinner(true)
        val email = loginEmailText.text.toString()
        val password = loginPasswordText.text.toString()
        hideKeyboard()

        if (email.isNotEmpty()&&password.isNotEmpty()) {
            AuthService.loginUser(this, email, password) { loginSuccess ->

                if (loginSuccess) {
                    AuthService.findUserByEmail(this) { findSuccess ->
                        if (findSuccess) {
                            enableSpinner(false)

                            val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                            LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                            finish()
                        } else {
                            errorToast()
                        }
                    }
                } else {
                    errorToast()
                }
            }
        }else{
            Toast.makeText(this, "Please fill in email and password", Toast.LENGTH_SHORT).show()
        }

    }

    fun loginCreateUserBtnClicked(view: View){
        val createUserIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createUserIntent)
        finish()

    }

    fun errorToast() {
        Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show()
        enableSpinner(false)
    }

    fun enableSpinner(enable: Boolean) {
        if (enable) {
            loginSpinner.visibility = View.VISIBLE
        } else {
            loginSpinner.visibility = View.INVISIBLE
        }
        loginLoginBtn.isEnabled = !enable
        loginCreateUserBtn.isEnabled = !enable
    }

    fun hideKeyboard(){
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (inputManager.isAcceptingText){
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken,0)
        }

    }

}
