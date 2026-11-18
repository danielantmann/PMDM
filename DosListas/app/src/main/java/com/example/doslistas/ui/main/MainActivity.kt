package com.example.doslistas.ui.main

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
import com.example.doslistas.di.AppModule
import com.example.doslistas.domain.model.Tarea
import com.example.doslistas.ui.theme.DosListasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DosListasTheme {
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
        onSelectTarea = viewModel::onSelectTarea,
        onAddTarea = viewModel::onAddTarea,
        onMoverTarea = viewModel::moverTareaEntreListas,
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
    onMoverTarea: () -> Unit,
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
                        Text("Gestión de Tareas")
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
            SeccionListaTareas(
                titulo = "Tareas Pendientes (${state.tareasPendientes.size})",
                tareas = state.tareasPendientes,
                tareaSeleccionado = state.tareaSeleccionado,
                onTareaClick = onSelectTarea,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            // --- 2. LISTA DE COMPLETADAS ---
            SeccionListaTareas(
                titulo = "Tareas Completadas (${state.tareasCompletadas.size})",
                tareas = state.tareasCompletadas,
                tareaSeleccionado = state.tareaSeleccionado,
                onTareaClick = onSelectTarea,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            // --- 3. FORMULARIO PARA AÑADIR TAREA ---
            FormularioTarea(
                tareaForm = state.tarea,
                onChangeTarea = onChangeTarea,
                onAddTarea = onAddTarea,
                modifier = Modifier.fillMaxWidth()
            )

            // --- 4. BOTONES INFERIORES (Pasar y Borrar) ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Botón para pasar la tarea seleccionada a la otra lista
                Button(
                    onClick = onMoverTarea,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("Pasar tarea")
                }

                // Botón para borrar la tarea seleccionada
                Button(
                    onClick = onBorrarTarea,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("Borrar")
                }
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
                Text("Añadir tarea")
            }
        }
    }
}

@Composable
fun TareaItemCard(
    tarea: Tarea,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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

            Surface(
                color = if (tarea.isTerminada) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.errorContainer,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (tarea.isTerminada) "Terminada" else "Pendiente",
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (tarea.isTerminada) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

@Composable
fun ListaTareas(
    tareas: List<Tarea>,
    tareaSeleccionado: Tarea?,
    onTareaClick: (Tarea) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = tareas,
            key = { tarea -> tarea.id }
        ) { tarea ->
            TareaItemCard(
                tarea = tarea,
                isSelected = tarea.id == tareaSeleccionado?.id,
                onClick = { onTareaClick(tarea) }
            )
        }
    }
}

@Composable
fun SeccionListaTareas(
    titulo: String,
    tareas: List<Tarea>,
    tareaSeleccionado: Tarea?,
    onTareaClick: (Tarea) -> Unit,
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

        ListaTareas(
            tareas = tareas,
            tareaSeleccionado = tareaSeleccionado,
            onTareaClick = onTareaClick
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

    DosListasTheme {
        MainPantallaContent(
            state = state,
            onChangeTarea = { nuevaTarea ->
                state = state.copy(tarea = nuevaTarea)
            },
            onSelectTarea = { tareaSeleccionado -> // <--- Nombre exacto de tu variable en el State
                state = state.copy(tareaSeleccionado = tareaSeleccionado)
            },
            onAddTarea = {
                val nueva = state.tarea.copy(id = System.currentTimeMillis(), isTerminada = false)
                if (nueva.nombre.isNotBlank()) {
                    state = state.copy(
                        tareasPendientes = state.tareasPendientes + nueva,
                        tarea = Tarea()
                    )
                }
            },
            onMoverTarea = {
                val sel = state.tareaSeleccionado ?: return@MainPantallaContent
                if (sel.isTerminada) {
                    state = state.copy(
                        tareasCompletadas = state.tareasCompletadas.filter { it.id != sel.id },
                        tareasPendientes = state.tareasPendientes + sel.copy(isTerminada = false),
                        tareaSeleccionado = null
                    )
                } else {
                    state = state.copy(
                        tareasPendientes = state.tareasPendientes.filter { it.id != sel.id },
                        tareasCompletadas = state.tareasCompletadas + sel.copy(isTerminada = true),
                        tareaSeleccionado = null
                    )
                }
            },
            onBorrarTarea = {
                val sel = state.tareaSeleccionado ?: return@MainPantallaContent
                state = state.copy(
                    tareasPendientes = state.tareasPendientes.filter { it.id != sel.id },
                    tareasCompletadas = state.tareasCompletadas.filter { it.id != sel.id },
                    tareaSeleccionado = null
                )
            },
            onClearMessages = {
                state = state.copy(error = null, mensaje = null)
            }
        )
    }
}