package com.example.cafeteriacompose.ui.main

import SeccionListaPedidos
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cafeteriacompose.di.AppModule
import com.example.cafeteriacompose.domain.model.Pedido
import com.example.cafeteriacompose.ui.componentes.FormPedidos
import com.example.cafeteriacompose.ui.theme.CafeteriaComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CafeteriaComposeTheme {
                MainPatnalla()
            }
        }
    }
}

@Composable
fun MainPatnalla(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(
            addPedidoUseCase = AppModule.addPedidoUseCase,
            deletePedidoUseCase = AppModule.deletePedidoUseCase,
            cargarPedidosUseCase = AppModule.cargarPedidosUseCase,
        )
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainPantallaContent(
        modifier = modifier,
        state = state,
        onChangePedido = viewModel::onChangePedido,
        onAddPedido = viewModel::onAddPedido,
        onSelectPedido = viewModel::onSelectPedido,
        onBorrarPedido = viewModel::onDeletePedido,
        onClearMessages = viewModel::clearMessages
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPantallaContent(
    modifier: Modifier = Modifier,
    state: MainState,
    onChangePedido: (Pedido) -> Unit,
    onAddPedido: () -> Unit,
    onSelectPedido: (Pedido) -> Unit,
    onBorrarPedido: () -> Unit,
    onClearMessages: () -> Unit = {}
){
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            onClearMessages()
        }
    }
    LaunchedEffect(state.mensaje) {
        state.mensaje?.let {
            snackbarHostState.showSnackbar(it)
            onClearMessages()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Pedidos Cafeteria")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp)
        ){
            SeccionListaPedidos(
                state = state,
                onPedidoClick = onSelectPedido,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            FormPedidos(
                state = state,
                onChangePedido = onChangePedido,
                onAddPedido = onAddPedido,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Button(
                onClick = onBorrarPedido,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Borrar Pedido seleccionado")
            }
        }
    }
}

//@Composable
//fun PedidoItemCard(
//    pedido: Pedido,
//    onClick: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onClick() }
//            .padding(vertical = 2.dp),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 10.dp, vertical = 6.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(36.dp)
//                    .background(
//                        color = MaterialTheme.colorScheme.primaryContainer,
//                        shape = CircleShape
//                    ),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = pedido.nombre.take(1).uppercase(),
//                    style = MaterialTheme.typography.labelLarge,
//                    color = MaterialTheme.colorScheme.onPrimaryContainer
//                )
//            }
//
//            Column(
//                modifier = Modifier.weight(1f),
//                verticalArrangement = Arrangement.spacedBy(0.dp)
//            ) {
//                Text(text = pedido.nombre, style = MaterialTheme.typography.titleSmall)
//
//                Text(
//                    text = pedido.producto,
//                    style = MaterialTheme.typography.labelSmall,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//            }
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(6.dp)
//            ) {
//                Text(
//                    text = "${pedido.precio}",
//                    style = MaterialTheme.typography.titleMedium,
//                    color = Color.Black
//                )
//                Surface(
//                    color = if (pedido.pagado) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
//                    shape = RoundedCornerShape(16.dp)
//                ) {
//                    Text(
//                        text = if (pedido.pagado) "Pagado" else "No Pagado",
//                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
//                        style = MaterialTheme.typography.labelSmall,
//                        color = if (pedido.pagado) Color(0xFF2E7D32) else Color(0xFFC62828)
//                    )
//                }
//            }
//        }
//    }
//}

//@Composable
//fun SeccionListaPedidos(
//    state: MainState,
//    onPedidoClick: (Pedido) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Column(modifier = modifier) {
//        Text(
//            text = "Lista de pedidos (${state.pedidos.size})",
//            style = MaterialTheme.typography.titleMedium,
//            color = MaterialTheme.colorScheme.onBackground,
//            modifier = Modifier.padding(bottom = 6.dp)
//        )
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f),
//            verticalArrangement = Arrangement.spacedBy(4.dp)
//        ) {
//            items(
//                items = state.pedidos,
//                key = { it.id }
//            ) { pedido ->
//                PedidoItemCard(
//                    pedido = pedido,
//                    onClick = { onPedidoClick(pedido) }
//                )
//            }
//        }
//    }
//}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun FormPedidos(
//    state: MainState,
//    onChangePedido: (Pedido) -> Unit,
//    onAddPedido: () -> Unit,
//    modifier: Modifier = Modifier
//){
//    val productos = listOf("Café", "Croissant", "Capuchino", "Chocolate", "Bagel")
//    var expanded by remember { mutableStateOf(false) }
//
//    OutlinedCard(modifier = modifier.fillMaxWidth()) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(10.dp),
//            verticalArrangement = Arrangement.spacedBy(6.dp)
//        ) {
//            Text(
//                text = "Añadir nuevo pedido",
//                style = MaterialTheme.typography.titleSmall,
//                color = MaterialTheme.colorScheme.primary
//            )
//            Text(
//                text = "Nombre",
//                style = MaterialTheme.typography.bodySmall,
//            )
//            OutlinedTextField(
//                value = state.pedido.nombre,
//                onValueChange = { onChangePedido(state.pedido.copy(nombre = it)) },
//                label = { Text("Nombre") },
//                leadingIcon = { Icon(Icons.Default.MenuBook, contentDescription = null) },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//
//            Text(
//                text = "Producto",
//                style = MaterialTheme.typography.bodySmall
//            )
//
//            ExposedDropdownMenuBox(
//                expanded = expanded,
//                onExpandedChange = { expanded = !expanded }
//            ) {
//                OutlinedTextField(
//                    value = state.pedido.producto,
//                    onValueChange = {},
//                    readOnly = true,
//                    label = { Text("Producto") },
//                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .menuAnchor()
//                )
//                ExposedDropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false }
//                ) {
//                    productos.forEach { opcion ->
//                        DropdownMenuItem(
//                            text = { Text(opcion) },
//                            onClick = {
//                                onChangePedido(state.pedido.copy(producto = opcion))
//                                expanded = false
//                            }
//                        )
//                    }
//                }
//            }
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                Column(Modifier.weight(1F)) {
//                    Text(
//                        text = "Cantidad",
//                        style = MaterialTheme.typography.bodySmall,
//                    )
//                    OutlinedTextField(
//                        value = if (state.pedido.cantidad == 0) "" else state.pedido.cantidad.toString(),
//                        onValueChange = { texto ->
//                            onChangePedido(state.pedido.copy(cantidad = texto.toIntOrNull() ?: 0))
//                        },
//                        label = { Text("Cantidad") },
//                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
//                        modifier = Modifier.fillMaxWidth(),
//                        singleLine = true
//                    )
//                }
//                Column(Modifier.weight(1F)) {
//                    Text(
//                        text = "Precio",
//                        style = MaterialTheme.typography.bodySmall,
//                    )
//                    OutlinedTextField(
//                        value = if (state.pedido.precio == 0.00) "" else state.pedido.precio.toString(),
//                        onValueChange = { texto ->
//                            onChangePedido(state.pedido.copy(precio = texto.toDoubleOrNull() ?: 0.00))
//                        },
//                        label = { Text("Precio") },
//                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
//                        modifier = Modifier.fillMaxWidth(),
//                        singleLine = true
//                    )
//                }
//            }
//
//            PedidoCheckbox(
//                checked = state.pedido.pagado,
//                onCheckedChange = { onChangePedido(state.pedido.copy(pagado = it)) }
//            )
//            Button(
//                onClick = onAddPedido,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
//                Spacer(Modifier.width(8.dp))
//                Text("Añadir Pedido")
//            }
//        }
//    }
//}
//
//@Composable
//fun PedidoCheckbox(
//    checked: Boolean,
//    onCheckedChange: (Boolean) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
//        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
//        Text(text = if (checked) "Pagado" else "No Pagado")
//    }
//}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CafeteriaComposeTheme {
        MainPatnalla()
    }
}