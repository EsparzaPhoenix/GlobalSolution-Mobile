package br.com.fiap.solartech.ui.dashboard

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.solartech.R

data class Pedido(
    val pedidoId: String = "",
    val nomeProduto: String = "",
    val modeloProduto: String = "",
    val tipoProduto: String = "",
    val marcaProduto: String = "",
    val precoUnitario: Double = 0.0
)

class PedidoAdapter(
    private var pedidos: List<Pedido>,
    private val onDetailsClicked: (Pedido) -> Unit,
    private val onDeleteClicked: (Pedido) -> Unit
) : RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder>() {

    inner class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeProdutoTextView: TextView = itemView.findViewById(R.id.nome_produto)
        val modeloProdutoTextView: TextView = itemView.findViewById(R.id.modelo_produto)
        val precoUnitarioTextView: TextView = itemView.findViewById(R.id.preco_unitario)
        val detailsButton: Button = itemView.findViewById(R.id.details_button)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pedido_item, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = pedidos[position]
        holder.nomeProdutoTextView.text = pedido.nomeProduto
        holder.modeloProdutoTextView.text = pedido.modeloProduto
        holder.precoUnitarioTextView.text = "Preço: R$ ${pedido.precoUnitario}"

        holder.detailsButton.setOnClickListener {
            onDetailsClicked(pedido)
        }
        holder.deleteButton.setOnClickListener {
            onDeleteClicked(pedido)
        }
    }

    override fun getItemCount(): Int {
        return pedidos.size
    }

    fun updatePedidos(newPedidos: List<Pedido>) {
        pedidos = newPedidos
        Log.d("PedidoAdapter", "Total de pedidos para exibição: ${pedidos.size}")
        notifyDataSetChanged()
    }
}
