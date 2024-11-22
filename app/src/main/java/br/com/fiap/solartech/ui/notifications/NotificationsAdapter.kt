package br.com.fiap.solartech.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.solartech.R
import br.com.fiap.solartech.ui.dashboard.Pedido

class NotificationsAdapter(private var completedPedidos: List<Pedido>) :
    RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder>() {

    class NotificationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pedidoTextView: TextView = itemView.findViewById(R.id.pedido_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_item, parent, false)
        return NotificationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        val pedido = completedPedidos[position]
        holder.pedidoTextView.text = "Pedido '${pedido.nomeProduto}' foi concluído."
    }

    override fun getItemCount(): Int {
        return completedPedidos.size
    }

    fun updatePedidos(newPedidos: List<Pedido>) {
        completedPedidos = newPedidos
        notifyDataSetChanged()
    }
}
