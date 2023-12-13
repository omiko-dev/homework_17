package com.example.homework_17.home

import androidx.navigation.fragment.findNavController
import com.example.homework_17.BaseFragment
import com.example.homework_17.databinding.FragmentHomeBinding
import com.example.homework_17.session.SessionManager


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val sessionManager = SessionManager

    override fun observer() {
        sessionObserver()
    }

    override fun listener() {
        binding.btnLogOut.setOnClickListener {
            sessionManager.clearSessionData()
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    private fun sessionObserver() {
        binding.tvEmail.text = sessionManager.getSessionData().email
    }


}