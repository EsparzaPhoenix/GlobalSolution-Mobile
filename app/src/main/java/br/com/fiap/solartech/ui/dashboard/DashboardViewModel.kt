package br.com.fiap.solartech.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class DashboardViewModel : ViewModel() {

    private val _pedidos = MutableLiveData<List<Pedido>>()
    val pedidos: LiveData<List<Pedido>> get() = _pedidos

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    init {
        loadExistingPedidos()
    }

    private fun getUserPedidosReference() = firebaseAuth.currentUser?.let {
        database.child("user_pedidos").child(it.uid)
    }

    fun loadExistingPedidos() {
        _pedidos.value = emptyList()
        forceNotifyLiveData()

        val userPedidosRef = getUserPedidosReference() ?: return
        Log.d("DashboardViewModel", "Iniciando carregamento de pedidos.")

        userPedidosRef.get().addOnSuccessListener { snapshot ->
            val pedidosList = mutableListOf<Pedido>()
            for (pedidoSnapshot in snapshot.children) {
                val pedido = pedidoSnapshot.getValue(Pedido::class.java)
                pedido?.let {
                    Log.d("DashboardViewModel", "Processando pedido: ${it.nomeProduto}")
                    pedidosList.add(it)
                }
            }
            _pedidos.value = pedidosList.toList()
            Log.d("DashboardViewModel", "Total de pedidos carregados: ${pedidosList.size}")
            forceNotifyLiveData()
        }.addOnFailureListener { exception ->
            Log.e("DashboardViewModel", "Erro ao carregar pedidos: ${exception.message}")
        }
    }

    fun requestNewPedido(nomeProduto: String, modeloProduto: String) {
        val pedidoId = UUID.randomUUID().toString()

        val newPedido = Pedido(
            pedidoId = pedidoId,
            nomeProduto = nomeProduto,
            modeloProduto = modeloProduto,
            tipoProduto = "Exemplo",
            marcaProduto = "Marca Exemplo",
            precoUnitario = 0.0,
        )

        _pedidos.value = _pedidos.value?.plus(newPedido) ?: listOf(newPedido)
        savePedidoToRealtimeDatabase(pedidoId, newPedido)
    }

    private fun savePedidoToRealtimeDatabase(pedidoId: String, pedido: Pedido) {
        val userPedidosRef = getUserPedidosReference()?.child(pedidoId) ?: return

        userPedidosRef.setValue(pedido)
            .addOnSuccessListener {
                Log.d("DashboardViewModel", "Pedido salvo com sucesso")
                val updatedPedido = pedido.copy(tipoProduto = "Atualizado")
                _pedidos.value = _pedidos.value?.map {
                    if (it.pedidoId == pedido.pedidoId) updatedPedido else it
                }
                forceNotifyLiveData()
            }
            .addOnFailureListener { exception ->
                Log.e("DashboardViewModel", "Erro ao salvar pedido: ${exception.message}")
            }
    }

    fun clearPedidos() {
        _pedidos.value = emptyList()
        Log.d("DashboardViewModel", "Pedidos limpos após logout.")
        forceNotifyLiveData()
    }

    fun reloadPedidos() {
        clearPedidos()
        loadExistingPedidos()
    }

    private fun forceNotifyLiveData() {
        _pedidos.value = _pedidos.value
    }

    fun deletePedido(pedido: Pedido) {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val databaseRef = FirebaseDatabase.getInstance().reference.child("user_pedidos").child(userId).child(pedido.pedidoId)

            databaseRef.removeValue().addOnSuccessListener {
                _pedidos.value = _pedidos.value?.filterNot { it.pedidoId == pedido.pedidoId }
                Log.d("DashboardViewModel", "Pedido deletado com sucesso do Firebase Realtime Database.")
            }.addOnFailureListener { exception ->
                Log.e("DashboardViewModel", "Erro ao deletar pedido: ${exception.message}")
            }
        }
    }

    fun getCurrentUser() = firebaseAuth.currentUser
}
