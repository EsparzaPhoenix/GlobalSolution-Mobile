package br.com.fiap.solartech.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.fiap.solartech.databinding.FragmentPedidoDetailsBinding

class PedidoDetailsFragment : Fragment() {

    private var _binding: FragmentPedidoDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPedidoDetailsBinding.inflate(inflater, container, false)

        val produtoNome = arguments?.getString("produtoNome")
        val produtoModelo = arguments?.getString("produtoModelo")

        binding.pedidoDetailsTextView.text =
            "Detalhes do pedido:\nProduto: $produtoNome\nModelo: $produtoModelo."

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
