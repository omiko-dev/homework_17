package com.example.homework_17.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.homework_17.BaseFragment
import com.example.homework_17.databinding.FragmentHomeBinding
import com.example.homework_17.datastore.UserDataSerializer
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    override fun observer() {
        sessionObserver()
    }

    override fun listener() {
        binding.btnLogOut.setOnClickListener {
            viewModel.clearUserData()
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    private fun sessionObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                UserDataSerializer.userDataFlow.collect{
                    binding.tvEmail.text = it.email
                }
            }
        }
    }
}