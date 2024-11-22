package br.com.fiap.solartech.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.findNavController
import br.com.fiap.solartech.databinding.FragmentRegisterBinding
import br.com.fiap.solartech.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Configura o botão de cadastro
        binding.registerButton.setOnClickListener {
            val email = binding.email.text.toString()
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(context, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(context, "A senha deve ter pelo menos 6 caracteres.", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(context, "As senhas não coincidem.", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, username, password)
            }
        }

        return binding.root
    }

    private fun createAccount(email: String, username: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_register_to_login)
                } else {
                    Toast.makeText(context, "Erro ao cadastrar: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUsernameToFirestore(username: String) {
        val userId = auth.currentUser?.uid
        val user = hashMapOf(
            "username" to username,
            "email" to auth.currentUser?.email
        )

        if (userId != null) {
            firestore.collection("users").document(userId).set(user)
                .addOnSuccessListener {
                    Toast.makeText(context, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    Log.d("RegisterFragment", "Redirecionando para a tela de login")

                    requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                        .navigate(R.id.action_register_to_login)

                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Erro ao salvar dados: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
