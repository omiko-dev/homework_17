package com.example.homework_17.presentation.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework_17.BaseFragment
import com.example.homework_17.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val viewModel: SplashViewModel by viewModels()

    override fun observer() {
        navigationObserver()
    }

    override fun listener() {}

    private fun navigationObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userModelFlow.collect {
                    when(it) {
                        is NavigationFromSplashEvent.NavigationToHome -> {
                            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                        }
                        is NavigationFromSplashEvent.NavigationToLogin -> {
                            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                        }
                    }
                }
            }
        }
    }
}