import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cafeteriacompose.domain.model.Pedido
import com.example.cafeteriacompose.ui.componentes.PedidoItemCard
import com.example.cafeteriacompose.ui.main.MainState


@Composable
fun SeccionListaPedidos(
    state: MainState,
    onPedidoClick: (Pedido) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Lista de pedidos (${state.pedidos.size})",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(
                items = state.pedidos,
                key = { it.id }
            ) { pedido ->
                PedidoItemCard(
                    pedido = pedido,
                    onClick = { onPedidoClick(pedido) }
                )
            }
        }
    }
}