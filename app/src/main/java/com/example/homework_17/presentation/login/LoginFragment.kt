package com.example.homework_17.presentation.login

import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework_17.BaseFragment
import com.example.homework_17.common.ErrorMessage
import com.example.homework_17.common.Resource
import com.example.homework_17.databinding.FragmentLoginBinding
import com.example.homework_17.domain.datastore_proto.DataStoreRequest
import com.example.homework_17.domain.log_in.LoginRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()
    private val emailRegex: Regex = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
    private lateinit var loginRequest: LoginRequest

    private fun fillRegisterData() {
        setFragmentResultListener("register_data") { _, bundle ->
            with(binding) {
                etEmail.setText(bundle.getString("email"))
                etPassword.setText(bundle.getString("password"))
            }
        }
    }

    private fun loginClick() {
        binding.btnLogin.setOnClickListener {
            with(binding) {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (fieldChecker(email, password)) {
                    loginRequest = LoginRequest(email = email, password = password)
                    viewModel.onEvent(
                        LogInEvent.LogIn(loginRequest)
                    )
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
                        is Resource.Success -> {
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
            val dataStoreRequest = if (cbRemember.isChecked) {
                DataStoreRequest(true, loginRequest.email)
            } else {
                DataStoreRequest(false, loginRequest.email)
            }
            viewModel.setDataStore(dataStoreRequest)
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