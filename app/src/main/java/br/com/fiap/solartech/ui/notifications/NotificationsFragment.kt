package br.com.fiap.solartech.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.solartech.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.addPedidoButton.setOnClickListener {
            val nomeProduto = binding.produtoNomeInput.text.toString()
            val modeloProduto = binding.produtoModeloInput.text.toString()
            val tipoProduto = binding.tipoProdutoInput.text.toString()
            val marcaProduto = binding.marcaProdutoInput.text.toString()
            val valorUnitario = binding.valorUnitarioInput.text.toString().toDoubleOrNull()

            if (nomeProduto.isBlank() || modeloProduto.isBlank() || tipoProduto.isBlank() ||
                marcaProduto.isBlank() || valorUnitario == null) {
                Toast.makeText(requireContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            } else {
                notificationsViewModel.addPedido(
                    nomeProduto = nomeProduto,
                    modeloProduto = modeloProduto,
                    tipoProduto = tipoProduto,
                    marcaProduto = marcaProduto,
                    valorUnitario = valorUnitario
                )
                Toast.makeText(requireContext(), "Pedido adicionado com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
