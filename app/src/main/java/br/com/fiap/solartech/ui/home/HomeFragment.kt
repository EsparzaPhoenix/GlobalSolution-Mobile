package br.com.fiap.solartech.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.fiap.solartech.R
import br.com.fiap.solartech.databinding.FragmentHomeBinding
import br.com.fiap.solartech.ui.dashboard.DashboardViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        dashboardViewModel = ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_login)
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_register)
        }

        binding.logoutButton.setOnClickListener {
            logoutUser()
        }

        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                dashboardViewModel.reloadPedidos()
                Toast.makeText(context, "Bem-vindo de volta!", Toast.LENGTH_SHORT).show()
            } else {
                dashboardViewModel.clearPedidos()
            }
            checkUserStatus()
        }

        auth.addAuthStateListener(authStateListener)

        checkUserStatus()

        return binding.root
    }

    private fun checkUserStatus() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            binding.logoutButton.visibility = View.VISIBLE
            binding.loginButton.visibility = View.GONE
            binding.registerButton.visibility = View.GONE
        } else {
            binding.logoutButton.visibility = View.GONE
            binding.loginButton.visibility = View.VISIBLE
            binding.registerButton.visibility = View.VISIBLE
        }
    }

    private fun logoutUser() {
        auth.signOut()
        dashboardViewModel.clearPedidos()
        checkUserStatus()
        Toast.makeText(context, "Você saiu da conta", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        auth.removeAuthStateListener(authStateListener)
        _binding = null
    }
}
