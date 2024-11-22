package br.com.fiap.solartech.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fiap.solartech.databinding.FragmentDashboardBinding
import br.com.fiap.solartech.R

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var pedidoAdapter: PedidoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configurando o adaptador de pedidos
        pedidoAdapter = PedidoAdapter(
            pedidos = listOf(),
            onDetailsClicked = { pedido ->
                val bundle = Bundle()
                bundle.putString("produtoNome", pedido.nomeProduto)
                bundle.putString("produtoModelo", pedido.modeloProduto)
                bundle.putDouble("precoUnitario", pedido.precoUnitario)
                findNavController().navigate(R.id.pedidoDetailsFragment, bundle)
            },
            onDeleteClicked = { pedido ->
                dashboardViewModel.deletePedido(pedido)
                Toast.makeText(requireContext(), "Pedido '${pedido.nomeProduto}' excluído", Toast.LENGTH_SHORT).show()
            }
        )

        // Configurando RecyclerView
        binding.pedidoList.layoutManager = LinearLayoutManager(requireContext())
        binding.pedidoList.adapter = pedidoAdapter

        // Observando mudanças nos pedidos
        dashboardViewModel.pedidos.observe(viewLifecycleOwner) { pedidos ->
            pedidoAdapter.updatePedidos(pedidos)
        }

        checkUserStatus()

        // Solicitar novo pedido
        binding.newPedidoButton.setOnClickListener {
            val produtoNome = binding.produtoNomeInput.text.toString()
            val produtoModelo = binding.produtoModeloInput.text.toString()
            if (produtoNome.isNotBlank() && produtoModelo.isNotBlank()) {
                dashboardViewModel.requestNewPedido(produtoNome, produtoModelo)
                Toast.makeText(requireContext(), "Pedido solicitado para $produtoNome ($produtoModelo)", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Por favor, preencha todos os campos do pedido", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun checkUserStatus() {
        val currentUser = dashboardViewModel.getCurrentUser()
        if (currentUser != null) {
            binding.produtoNomeInput.visibility = View.VISIBLE
            binding.produtoModeloInput.visibility = View.VISIBLE
            binding.newPedidoButton.visibility = View.VISIBLE
            binding.notLoggedInMessage.visibility = View.GONE
        } else {
            dashboardViewModel.clearPedidos()
            binding.produtoNomeInput.visibility = View.GONE
            binding.produtoModeloInput.visibility = View.GONE
            binding.newPedidoButton.visibility = View.GONE
            binding.notLoggedInMessage.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
