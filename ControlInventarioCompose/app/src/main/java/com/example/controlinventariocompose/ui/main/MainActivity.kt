package com.example.controlinventariocompose.ui.main

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.controlinventariocompose.ui.theme.ControlInventarioComposeTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.controlinventariocompose.di.AppModule
import com.example.controlinventariocompose.domain.model.Producto


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ControlInventarioComposeTheme {
                MainPantalla()

            }
        }
    }
}


@Composable
fun MainPantalla(modifier: Modifier = Modifier,
                 viewModel: MainViewModel = viewModel(
                     factory = MainViewModelFactory(
                         addProductoUseCase = AppModule.addProductoUseCase,
                         deleteProductoUseCase = AppModule.deleteProductoUseCase,
                         cargarProductosUseCase = AppModule.cargarProductosUseCase
                     )
                 )
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainPantallaContent(
        modifier = modifier,
        state = state,
        onChangeProducto = viewModel::onChangeProducto,
        onAddProducto = viewModel::onAddProducto,
        onBorrarProducto = viewModel::onDeleteProducto
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPantallaContent(
    modifier: Modifier = Modifier,
    state: MainState,
    onChangeProducto: (Producto) -> Unit,
    onAddProducto: () -> Unit,
    onBorrarProducto: (Producto) -> Unit
){

    var productoSeleccionado by remember { mutableStateOf<Producto?>(null) }
    Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
        TopAppBar(
            title = { Text("\uD83D\uDCE6 Control de Inventario") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                FormularioProducto(
                    state =state,
                    modifier = modifier,
                    onAddProducto = onAddProducto,
                    onChangeProducto = onChangeProducto
                )

                SeccionListaProductos(
                    state = state,
                    onProductoClick = { productoClickeado ->
                        productoSeleccionado = productoClickeado
                    }
                )


            }
            Button(onClick = {
                productoSeleccionado?.let { producto ->
                    onBorrarProducto(producto)
                    productoSeleccionado= null
                }
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                modifier = Modifier.fillMaxWidth(),

                ) {
                Text("🗑️ Borrar producto seleccionado")
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ControlInventarioComposeTheme {
        MainPantalla()
    }
}

@Composable
fun FormularioProducto(
    state: MainState,
    modifier: Modifier = Modifier,
    onAddProducto: () -> Unit,
    onChangeProducto: (Producto) -> Unit
){
    OutlinedCard(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = "Añadir producto",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(Modifier.weight(1f)) {
                    Text(text = "Producto: ")
                    OutlinedTextField(
                        value = state.producto.nombre,
                        onValueChange = {onChangeProducto(state.producto.copy(nombre = it))},
                        label = {Text("Producto")}
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(Modifier.weight(1.8f)) {
                    Text(text = "Categoria: ")
                    OutlinedTextField(
                        value = state.producto.categoria,
                        onValueChange = {onChangeProducto(state.producto.copy(categoria = it))},
                        label = {Text("Categoria")}
                    )
                }
                Column(Modifier.weight(1f)) {
                    Text(text = "Precio: ")
                    OutlinedTextField(
                        value = if (state.producto.precio == 0.0) "" else state.producto.precio.toString(),
                        onValueChange = { texto ->
                            // Si está vacío ponemos 0.0, si escribe un número válido lo actualizamos
                            val precioValido = texto.toDoubleOrNull() ?: 0.0
                            onChangeProducto(state.producto.copy(precio = precioValido))
                        },
                        label = { Text("Precio") }
                    )
                }
                Column(Modifier.weight(1f)) {
                    Text(text = "Stock: ")
                    OutlinedTextField(
                        value = if (state.producto.stock == 0) "" else state.producto.stock.toString(),
                        onValueChange = { texto ->
                            val stockValido = texto.toIntOrNull() ?: 0
                            onChangeProducto(state.producto.copy(stock = stockValido))
                        },
                        label = { Text("Stock") }
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text(text = "Disponible: ")
                    ProductoCheckbox(
                        checked = state.producto.disponible,
                        onCheckedChange = {nuevoValor ->
                            onChangeProducto(state.producto.copy(disponible = nuevoValor))
                        })

                }
                Column(Modifier.weight(1f)) {
                    Button(
                        onClick = onAddProducto , // Ejecuta la función de añadir del ViewModel
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Añadir Producto")
                    }
                }

            }

        }
    }
}

@Composable
fun ProductoCheckbox(
    checked: Boolean,                         // Recibe si está activo o no desde el estado
    onCheckedChange: (Boolean) -> Unit,       // Avisa al ViewModel cuando cambia
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        // Usamos el Checkbox oficial de Material 3
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = if (checked) "Producto disponible" else "No disponible"
        )
    }
}

@Composable
fun ProductoItemCard(
    producto: Producto,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{ onClick()}
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        // Un Row para organizar los elementos en horizontal (izquierda, centro, derecha)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp) // Espacio entre el círculo y los textos
        ){
            // 1. EL CÍRCULO CON LAS CANTIDADES
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color = MaterialTheme.colorScheme.primaryContainer, shape = androidx.compose.foundation.shape.CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Aquí pintaríamos las iniciales (ej: "LA")
                Text(
                    text = "${producto.stock}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            // 2. LOS TEXTOS DEL MEDIO
            // Usamos .weight(1f) para que ocupe todo el ancho disponible empujando lo de la derecha
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${producto.categoria} • ${producto.precio} €",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // 3. LA PARTE DERECHA (Badge de activo y flechita)

            Surface(
                color = if (producto.disponible) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = if (producto.disponible) "Disponible" else "No disponible",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (producto.disponible) Color(0xFF2E7D32) else Color(0xFFC62828)
                )
            }
        }
    }
}

@Composable
fun ListaProductos(
    productos: List<Producto>,
    onProductoClick: (Producto) -> Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items= productos,
            key={ producto -> producto.nombre}
        ){
            producto ->
            ProductoItemCard(
                producto = producto,
                onClick = { onProductoClick(producto)}
            )
        }
    }
}

@Composable
fun SeccionListaProductos(
    state: MainState,
    onProductoClick: (Producto) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = ("Lista de productos( ${state.productos.size})"),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        ListaProductos(
            productos = state.productos,
            onProductoClick = onProductoClick
        )
    }
}