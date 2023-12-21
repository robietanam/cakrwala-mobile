package com.bangkit.cakrawala.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bangkit.cakrawala.data.response.Auth
import com.bangkit.cakrawala.data.response.ResponseStatus
import com.bangkit.cakrawala.databinding.ActivityLoginBinding
import com.bangkit.cakrawala.ui.AuthViewModelFactory
import com.bangkit.cakrawala.ui.common.TokenViewModel
import com.bangkit.cakrawala.ui.components.ButtonWithLoadingCustom
import com.bangkit.cakrawala.ui.home.HomeActivity
import com.bangkit.cakrawala.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var myButton: ButtonWithLoadingCustom
    private lateinit var tokenViewModel: TokenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myButton = binding.btnSubmit

        tokenViewModel = ViewModelProvider(this, AuthViewModelFactory.getInstance(this))[TokenViewModel::class.java]

        loginViewModel = ViewModelProvider(this, AuthViewModelFactory.getInstance(this))[LoginViewModel::class.java]
        loginViewModel.auth.observe(this){ it ->
            if (it.status == ResponseStatus.Error){
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            } else {
                if (it.data != null){
                    val data = it.data
                    tokenViewModel.saveToken(Auth(userName = data.userName, userId = data.userId, token = data.token, premium = data.premium))

                    tokenViewModel.getToken().observe(this){
                        if (it?.token != null){
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                    }
                }
            }
        }

        checkIsNotEmpty()

        loginViewModel.isLoading.observe(this){
            setMyButtonLoading(it)
        }
        binding.btnRegister.setOnClickListener {
            val intentDetail = Intent(this, RegisterActivity::class.java)
            startActivity(intentDetail)
        }

        myButton = binding.btnSubmit

        binding.editTextCustom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkIsNotEmpty()
            }
            override fun afterTextChanged(s: Editable) {}
        })


        binding.passwordEditTextCustom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkIsNotEmpty()
            }
            override fun afterTextChanged(s: Editable) {}
        })


        myButton.setOnClickListener {
            if(checkIsNotEmpty()){

                loginViewModel.login(email = binding.editTextCustom.text.toString(), password = binding.passwordEditTextCustom.text.toString())
            } else {
                Toast.makeText(this, "Mohon Masukkan semua data dengan benar", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun checkIsNotEmpty() : Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z0-9.-]\$"
        if (!binding.editTextCustom.text.isNullOrEmpty() && !binding.passwordEditTextCustom.text.isNullOrEmpty() ) {
            if (binding.passwordEditTextCustom.text!!.length >= 8 && binding.editTextCustom.text!!.matches(emailRegex.toRegex())){
                setMyButtonEnable(true)
                return true
            } else {
                setMyButtonEnable(false)
            }
        } else {
            setMyButtonEnable(false)
        }
        return false
    }

    private fun setMyButtonLoading(value: Boolean){
        setMyButtonEnable(false)
        myButton.setLoading(value)
    }

    private fun setMyButtonEnable(value: Boolean) {
        myButton.isEnabled = value
    }

}