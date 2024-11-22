package br.com.fiap.solartech.ui.notifications

import androidx.lifecycle.ViewModel
import br.com.fiap.solartech.ui.dashboard.Pedido
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class NotificationsViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    fun addPedido(
        nomeProduto: String,
        modeloProduto: String,
        tipoProduto: String,
        marcaProduto: String,
        valorUnitario: Double
    ) {
        val pedidoId = System.currentTimeMillis().toString()
        val pedido = Pedido(
            pedidoId = pedidoId,
            nomeProduto = nomeProduto,
            modeloProduto = modeloProduto,
            tipoProduto = tipoProduto,
            marcaProduto = marcaProduto,
            precoUnitario = valorUnitario
        )

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val pedidosRef = database.child("user_pedidos").child(userId).child(pedidoId)

            pedidosRef.setValue(pedido)
                .addOnSuccessListener {
                    // Pedido salvo com sucesso
                }
                .addOnFailureListener { exception ->
                    // Lidar com falha
                }
        }
    }
}
