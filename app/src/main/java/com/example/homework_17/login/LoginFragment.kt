package com.example.homework_17.login

import android.os.Build
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework_17.BaseFragment
import com.example.homework_17.common.Resource
import com.example.homework_17.databinding.FragmentLoginBinding
import com.example.homework_17.dto.AuthDto
import com.example.homework_17.model.ErrorMessage
import com.example.homework_17.model.User
import com.example.homework_17.session.SessionData
import com.example.homework_17.session.SessionManager
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()
    private val emailRegex: Regex = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
    private lateinit var authDto: AuthDto

    private fun fillRegisterData() {
        setFragmentResultListener("authData") { _, bundle ->
            val resultValue = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("authData", AuthDto::class.java)
            } else {
                bundle.getParcelable("authData")
            }
            with(binding) {
                etEmail.setText(resultValue?.email)
                etPassword.setText(resultValue?.password)
            }
        }
    }

    private fun checkSession() {
        viewLifecycleOwner.lifecycleScope.launch {
            if(SessionManager.getSessionData().isLoggedIn){
                val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }
    }


    private fun loginClick() {
        binding.btnLogin.setOnClickListener {
            with(binding) {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (fieldChecker(email, password)) {
                    authDto = AuthDto(email = email, password = password)
                    viewModel.login(authDto)
                }
            }
        }
    }

    private fun fieldChecker(email: String, password: String): Boolean {
        with(binding) {
            if (email.isBlank() || password.isBlank()) {
                tvError.text = ErrorMessage.ENTER_ALL_FIELD.message
                return false
            }
            if (!emailRegex.matches(email)) {
                tvError.text = ErrorMessage.ENTER_CORRECT_EMAIL.message
                return false
            }
            if (password.length < 6) {
                tvError.text = ErrorMessage.ENTER_CORRECT_PASSWORD.message
                return false
            }
            tvError.text = null
            return true
        }
    }

    private fun goToRegister() {
        binding.btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }

    private fun userObservable() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userFlow.collect {
                    when (it) {
                        is Resource.Loading -> showLoader()
                        is Resource.Success<User> -> {
                            saveInSession()
                            goToHome()
                        }
                        is Resource.Error -> {
                            binding.tvError.text = it.error
                        }
                        is Resource.Idle -> hideLoader()
                    }
                }
            }
        }
    }

    private fun saveInSession() {
        with(binding) {
            val sessionData = if (cbRemember.isChecked) {
                SessionData(true, authDto.email)
            } else {
                SessionData(false, authDto.email)
            }
            viewModel.saveSessionData(sessionData)
        }
    }

    private fun goToHome() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(action)
    }


    private fun showLoader() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        binding.progressBar.visibility = View.GONE
    }

    override fun observer() {
        checkSession()
        userObservable()
    }

    override fun listener() {
        loginClick()
        goToRegister()
    }

    override fun bind() {
        fillRegisterData()
    }

}