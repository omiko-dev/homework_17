package com.example.homework_17.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework_17.BaseFragment
import com.example.homework_17.databinding.FragmentSplashBinding
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun observer() {
        test()
    }

    override fun listener() {}

    private fun test() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userModelFlow.collect {
                    goToPage(it)
                }
            }
        }
    }

    private fun goToPage(isAuth: Boolean) {
        if (isAuth)
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
        else
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
    }


}