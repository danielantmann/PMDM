package com.example.doslistastres.ui.main

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doslistastres.domain.model.Tarea
import com.example.doslistastres.di.AppModule
import com.example.doslistastres.ui.theme.DosListasTresTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DosListasTresTheme {
                MainPantalla()
            }
        }
    }
}

@Composable
fun MainPantalla(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(
            addTareaUseCase = AppModule.addTareaUseCase,
            deleteTareaUseCase = AppModule.deleteTareaUseCase,
            cargarTareasUseCase = AppModule.cargarTareasUseCase
        )
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainPantallaContent(
        state = state,
        onChangeTarea = viewModel::onChangeTarea,
        onSelectTarea = { tarea ->
            viewModel.onSelectTarea(tarea)
            // Opcional: si quieres que al seleccionarla también se cargue en el form automáticamente
            viewModel.onChangeTarea(tarea)
        },
        onAddTarea = viewModel::onAddTarea,
        onMoverDirecto = viewModel::moverTareaDirecta,
        onBorrarTarea = viewModel::onDeleteTarea,
        onClearMessages = viewModel::clearMessages,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPantallaContent(
    modifier: Modifier = Modifier,
    state: MainState,
    onChangeTarea: (Tarea) -> Unit,
    onSelectTarea: (Tarea) -> Unit,
    onAddTarea: () -> Unit,
    onMoverDirecto: (Tarea) -> Unit,
    onBorrarTarea: () -> Unit,
    onClearMessages: () -> Unit
) {
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
                            imageVector = Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Gestión de Tareas - Tres")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // --- 1. LISTA DE PENDIENTES ---
            SeccionListaTareas2(
                titulo = "Tareas Pendientes (${state.tareasPendientes.size})",
                tareas = state.tareasPendientes,
                tareaSeleccionado = state.tareaSeleccionado,
                onSelectClick = onSelectTarea,
                onMoverTarea = onMoverDirecto,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            // --- 2. LISTA DE COMPLETADAS ---
            SeccionListaTareas2(
                titulo = "Tareas Completadas (${state.tareasCompletadas.size})",
                tareas = state.tareasCompletadas,
                tareaSeleccionado = state.tareaSeleccionado,
                onSelectClick = onSelectTarea,
                onMoverTarea = onMoverDirecto,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            // --- 3. PANEL DE DETALLE DE LA TAREA SELECCIONADA ---
            state.tareaSeleccionado?.let { tareaSel ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(text = "📌 Detalle de la Tarea Seleccionada:", style = MaterialTheme.typography.titleSmall)
                        Text(text = "ID: ${tareaSel.id}", style = MaterialTheme.typography.bodySmall)
                        Text(text = "Nombre: ${tareaSel.nombre}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Descripción: ${tareaSel.descripcion}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            // --- 4. FORMULARIO PARA AÑADIR / EDITAR TAREA ---
            FormularioTarea(
                tareaForm = state.tarea,
                onChangeTarea = onChangeTarea,
                onAddTarea = onAddTarea,
                modifier = Modifier.fillMaxWidth()
            )

            // --- 5. BOTÓN INFERIOR PARA BORRAR ---
            Button(
                onClick = onBorrarTarea,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text("Borrar seleccionada")
            }
        }
    }
}

@Composable
fun FormularioTarea(
    tareaForm: Tarea,
    onChangeTarea: (Tarea) -> Unit,
    onAddTarea: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                OutlinedTextField(
                    value = tareaForm.nombre,
                    onValueChange = { onChangeTarea(tareaForm.copy(nombre = it)) },
                    label = { Text("Nombre") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = tareaForm.descripcion,
                    onValueChange = { onChangeTarea(tareaForm.copy(descripcion = it)) },
                    label = { Text("Descripción") },
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                onClick = onAddTarea,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Guardar / Añadir")
            }
        }
    }
}

// =========================================================================
// TARJETA SELECTIVA Y CON BOTÓN DE MOVER
// =========================================================================

@Composable
fun TareaItemCard2(
    tarea: Tarea,
    isSelected: Boolean,
    onSelectClick: () -> Unit,
    onMoverClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectClick() } // Al hacer clic se selecciona, se muestra el detalle y se pasa al form
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tarea.nombre.take(1).uppercase(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Text(
                    text = tarea.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = tarea.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Botón independiente para mover la tarea de lista
            Button(
                onClick = onMoverClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                modifier = Modifier.size(36.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.SwapHoriz,
                    contentDescription = "Pasar tarea",
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun ListaTareas2(
    tareas: List<Tarea>,
    tareaSeleccionado: Tarea?,
    onSelectClick: (Tarea) -> Unit,
    onMoverTarea: (Tarea) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(items = tareas, key = { it.id }) { tarea ->
            TareaItemCard2(
                tarea = tarea,
                isSelected = tarea.id == tareaSeleccionado?.id,
                onSelectClick = { onSelectClick(tarea) },
                onMoverClick = { onMoverTarea(tarea) }
            )
        }
    }
}

@Composable
fun SeccionListaTareas2(
    titulo: String,
    tareas: List<Tarea>,
    tareaSeleccionado: Tarea?,
    onSelectClick: (Tarea) -> Unit,
    onMoverTarea: (Tarea) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        ListaTareas2(
            tareas = tareas,
            tareaSeleccionado = tareaSeleccionado,
            onSelectClick = onSelectClick,
            onMoverTarea = onMoverTarea
        )
    }
}

@Preview(showBackground = true, name = "Preview Interactivo Tareas")
@Composable
fun GreetingPreview() {
    var state by remember {
        mutableStateOf(
            MainState(
                tareasPendientes = listOf(
                    Tarea(id = 1L, nombre = "Estudiar Compose", descripcion = "Repasar estados", isTerminada = false),
                    Tarea(id = 2L, nombre = "Examen MVVM", descripcion = "Arquitectura", isTerminada = false)
                ),
                tareasCompletadas = listOf(
                    Tarea(id = 3L, nombre = "Proyecto Base", descripcion = "Configurar Gradle", isTerminada = true)
                )
            )
        )
    }

    DosListasTresTheme {
        MainPantallaContent(
            state = state,
            onChangeTarea = { nuevaTarea ->
                state = state.copy(tarea = nuevaTarea)
            },
            onSelectTarea = { tareaSeleccionado ->
                state = state.copy(
                    tareaSeleccionado = tareaSeleccionado,
                    tarea = tareaSeleccionado // Rellena el formulario automáticamente al hacer clic
                )
            },
            onAddTarea = {
                val tareaForm = state.tarea
                if (tareaForm.nombre.isNotBlank()) {
                    if (tareaForm.id == 0L) {
                        // 1. CASO NUEVA: Si no tiene ID, crea una nueva
                        val nueva = tareaForm.copy(id = System.currentTimeMillis(), isTerminada = false)
                        state = state.copy(
                            tareasPendientes = state.tareasPendientes + nueva,
                            tarea = Tarea()
                        )
                    } else {
                        // 2. CASO EDITAR (UPDATE): Si ya tiene ID, busca la tarea y actualiza sus campos
                        state = state.copy(
                            tareasPendientes = state.tareasPendientes.map {
                                if (it.id == tareaForm.id) tareaForm else it
                            },
                            tareasCompletadas = state.tareasCompletadas.map {
                                if (it.id == tareaForm.id) tareaForm else it
                            },
                            tarea = Tarea(),
                            tareaSeleccionado = null
                        )
                    }
                }
            },
            onMoverDirecto = { tareaMovida ->
                if (tareaMovida.isTerminada) {
                    state = state.copy(
                        tareasCompletadas = state.tareasCompletadas.filter { it.id != tareaMovida.id },
                        tareasPendientes = state.tareasPendientes + tareaMovida.copy(isTerminada = false)
                    )
                } else {
                    state = state.copy(
                        tareasPendientes = state.tareasPendientes.filter { it.id != tareaMovida.id },
                        tareasCompletadas = state.tareasCompletadas + tareaMovida.copy(isTerminada = true)
                    )
                }
            },
            onBorrarTarea = {
                val sel = state.tareaSeleccionado ?: return@MainPantallaContent
                state = state.copy(
                    tareasPendientes = state.tareasPendientes.filter { it.id != sel.id },
                    tareasCompletadas = state.tareasCompletadas.filter { it.id != sel.id },
                    tareaSeleccionado = null,
                    tarea = Tarea()
                )
            },
            onClearMessages = {
                state = state.copy(error = null, mensaje = null)
            }
        )
    }
}