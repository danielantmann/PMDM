package com.example.bibliotecacompose.ui.main

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bibliotecacompose.di.AppModule
import com.example.bibliotecacompose.domain.model.Libro
import com.example.bibliotecacompose.ui.theme.BibliotecaComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BibliotecaComposeTheme {
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
            addLibroUseCase = AppModule.addLibroUseCase,
            deleteLibroUseCase = AppModule.deleteLibroUseCase,
            cargarLibrosUseCase = AppModule.cargarLibrosUseCase
        )
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainPantallaContent(
        modifier = modifier,
        state = state,
        onChangeLibro = viewModel::onChangeLibro,
        onAddLibro = viewModel::onAddLibro,
        onSelectLibro = viewModel::onSelectLibro,
        onBorrarLibro = viewModel::onDeleteLibro
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPantallaContent(
    modifier: Modifier = Modifier,
    state: MainState,
    onChangeLibro: (Libro) -> Unit,
    onAddLibro: () -> Unit,
    onSelectLibro: (Libro) -> Unit,
    onBorrarLibro: () -> Unit
) {


    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Biblioteca") },
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
        ) {
            // LISTA: solo esta lleva weight
            SeccionListaLibros(
                state = state,
                onLibroClick = onSelectLibro,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            // FORM: altura natural (compacto)
            FormLibros(
                state = state,
                onChangeLibro = onChangeLibro,
                onAddLibro = onAddLibro,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            // BORRAR: misma lógica que tenías
            Button(
                onClick = onBorrarLibro,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("🗑️ Borrar producto seleccionado")
            }
        }
    }
}

@Composable
fun LibroItemCard(
    libro: Libro,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = libro.nombre.take(1).uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Text(
                    text = libro.nombre,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = libro.autor,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${libro.anio}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Surface(
                    color = if (libro.disponible) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = if (libro.disponible) "Activo" else "Inactivo",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (libro.disponible) Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                }
                Text(
                    text = ">",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ListaLibros(
    libros: List<Libro>,
    onLibroClick: (Libro) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = libros,
            key = { libro -> libro.nombre }
        ) { libro ->
            LibroItemCard(
                libro = libro,
                onClick = { onLibroClick(libro) }
            )
        }
    }
}

@Composable
fun SeccionListaLibros(
    state: MainState,
    onLibroClick: (Libro) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "Lista de libros (${state.libros.size})",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        ListaLibros(
            libros = state.libros,
            onLibroClick = onLibroClick,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Composable
fun FormLibros(
    state: MainState,
    onChangeLibro: (Libro) -> Unit,
    onAddLibro: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Añadir nuevo libro",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField(
                value = state.libro.nombre,
                onValueChange = { onChangeLibro(state.libro.copy(nombre = it)) },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = state.libro.autor,
                onValueChange = { onChangeLibro(state.libro.copy(autor = it)) },
                label = { Text("Autor") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = if (state.libro.anio == 0) "" else state.libro.anio.toString(),
                onValueChange = { texto ->
                    val anioValido = texto.toIntOrNull() ?: 0
                    onChangeLibro(state.libro.copy(anio = anioValido))
                },
                label = { Text("Año") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = state.libro.categoria,
                onValueChange = { onChangeLibro(state.libro.copy(categoria = it)) },
                label = { Text("Categoria") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            LibroCheckbox(
                checked = state.libro.disponible,
                onCheckedChange = { nuevoValor ->
                    onChangeLibro(state.libro.copy(disponible = nuevoValor))
                }
            )
            Button(
                onClick = onAddLibro,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Añadir Libro")
            }
        }
    }
}

@Composable
fun LibroCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = if (checked) "Libro disponible" else "No disponible"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BibliotecaComposeTheme {
        MainPatnalla()
    }
}