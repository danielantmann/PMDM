package com.example.cafeteriacompose.ui.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cafeteriacompose.domain.model.Pedido
import com.example.cafeteriacompose.ui.main.MainState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPedidos(
    state: MainState,
    onChangePedido: (Pedido) -> Unit,
    onAddPedido: () -> Unit,
    modifier: Modifier = Modifier
){
    val productos = listOf("Café", "Croissant", "Capuchino", "Chocolate", "Bagel")
    var expanded by remember { mutableStateOf(false) }

    OutlinedCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Añadir nuevo pedido",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Nombre",
                style = MaterialTheme.typography.bodySmall,
            )
            OutlinedTextField(
                value = state.pedido.nombre,
                onValueChange = { onChangePedido(state.pedido.copy(nombre = it)) },
                label = { Text("Nombre") },
                leadingIcon = { Icon(Icons.Default.MenuBook, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text(
                text = "Producto",
                style = MaterialTheme.typography.bodySmall
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = state.pedido.producto,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Producto") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    productos.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                onChangePedido(state.pedido.copy(producto = opcion))
                                expanded = false
                            }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(Modifier.weight(1F)) {
                    Text(
                        text = "Cantidad",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    OutlinedTextField(
                        value = if (state.pedido.cantidad == 0) "" else state.pedido.cantidad.toString(),
                        onValueChange = { texto ->
                            onChangePedido(state.pedido.copy(cantidad = texto.toIntOrNull() ?: 0))
                        },
                        label = { Text("Cantidad") },
                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                Column(Modifier.weight(1F)) {
                    Text(
                        text = "Precio",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    OutlinedTextField(
                        value = if (state.pedido.precio == 0.00) "" else state.pedido.precio.toString(),
                        onValueChange = { texto ->
                            onChangePedido(state.pedido.copy(precio = texto.toDoubleOrNull() ?: 0.00))
                        },
                        label = { Text("Precio") },
                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }

            PedidoCheckbox(
                checked = state.pedido.pagado,
                onCheckedChange = { onChangePedido(state.pedido.copy(pagado = it)) }
            )
            Button(
                onClick = onAddPedido,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Añadir Pedido")
            }
        }
    }
}

@Composable
fun PedidoCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(text = if (checked) "Pagado" else "No Pagado")
    }
}