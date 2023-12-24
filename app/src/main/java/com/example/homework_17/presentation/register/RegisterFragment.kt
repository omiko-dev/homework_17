package com.example.homework_17.presentation.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework_17.BaseFragment
import com.example.homework_17.common.ErrorMessage
import com.example.homework_17.common.Resource
import com.example.homework_17.databinding.FragmentRegisterBinding
import com.example.homework_17.domain.register.RegisterRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterViewModel by viewModels()
    private val emailRegex: Regex = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
    private lateinit var registerRequest: RegisterRequest

    override fun observer() {
        userObserver()
    }

    override fun listener() {
        registerClickListener()
        goToBack()
    }


    private fun registerClickListener() {
        binding.btnRegister.setOnClickListener {
            with(binding) {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val repeatPassword = etRepeatPassword.text.toString().trim()

                if (fieldChecker(email, password, repeatPassword)) {
                    registerRequest = RegisterRequest(email = email, password = password)
                    viewModel.onEvent(
                        RegisterEvent.Register(registerRequest)
                    )
                }
            }
        }
    }

    private fun goToBack() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun userObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userFlow.collect {
                    when (it) {
                        is Resource.Success -> {
                            val bundle = Bundle().apply {
                                putString("email", registerRequest.email)
                                putString("password", registerRequest.password)
                            }
                            setFragmentResult("register_data", bundle)
                            findNavController().popBackStack()
                        }

                        is Resource.Error -> {
                            binding.tvError.text = it.error
                        }

                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Idle -> binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }


    private fun fieldChecker(email: String, password: String, repeatPassword: String): Boolean {
        with(binding) {
            if (email.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
                tvError.text = ErrorMessage.ENTER_ALL_FIELD.message
                return false
            }
            if (!emailRegex.matches(email)) {
                tvError.text = ErrorMessage.ENTER_CORRECT_EMAIL.message
                return false
            }
            if (password != repeatPassword) {
                tvError.text = ErrorMessage.ENTER_CORRECT_REPEAT_PASSWORD.message
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
}