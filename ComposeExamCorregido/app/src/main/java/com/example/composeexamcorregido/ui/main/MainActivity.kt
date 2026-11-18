package com.example.composeexamcorregido.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.composeexamcorregido.di.AppMopdule
import com.example.composeexamcorregido.domain.model.Alumno
import com.example.composeexamcorregido.ui.theme.ComposeExamCorregidoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeExamCorregidoTheme {
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
            addAlumnoUseCase = AppMopdule.addAlumnoUseCase,
            cargarAlumnosUseCase = AppMopdule.cargarAlumnosUseCase,
            deleteAlumnoUseCase = AppMopdule.deleteAlumnoUseCase
        )
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainPantallaContent(
        state = state,
        onChangeAlumno = viewModel::onChangeAlumno,
        onSelectAlumno = viewModel::onSelectAlumno,
        onAddAlumno = viewModel::onAddAlumno,
        onBorrarAlumno = viewModel::onDeleteAlumno,
        onClearMessages = viewModel::clearMessages,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPantallaContent(
    modifier: Modifier = Modifier,
    state: MainState,
    onChangeAlumno: (Alumno) -> Unit,
    onSelectAlumno: (Alumno) -> Unit,
    onAddAlumno: () -> Unit,
    onBorrarAlumno: () -> Unit,
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
                        Text("Gestión de alumnos")
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
                .padding(12.dp)
        ) {
            SeccionListaAlumnos(
                alumnos = state.alumnos,
                alumnoSeleccionado = state.alumnoSeleccionado,
                onAlumnoClick = onSelectAlumno,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            FormularioAlumno(
                alumnoForm = state.alumno,
                onChangeAlumno = onChangeAlumno,
                onAddAlumno = onAddAlumno,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Button(
                onClick = onBorrarAlumno,
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
                Spacer(Modifier.width(8.dp))
                Text("Borrar Alumno seleccionado")
            }
        }
    }
}

@Composable
fun AlumnoCheckbox(
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
            text = if (checked) "Alumno activo" else "Inactivo"
        )
    }
}

@Composable
fun AlumnoItemCard(
    alumno: Alumno,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color = MaterialTheme.colorScheme.primaryContainer, shape = androidx.compose.foundation.shape.CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${alumno.nombre.take(1)}${alumno.apellido.take(1)}".uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "${alumno.nombre} ${alumno.apellido}",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${alumno.curso} • ${alumno.edad} años",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = alumno.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    color = if (alumno.isActive) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.errorContainer,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = if (alumno.isActive) "Activo" else "Inactivo",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (alumno.isActive) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onErrorContainer
                    )
                }

                Text(
                    text = ">",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
fun ListaAlumnos(
    alumnos: List<Alumno>,
    alumnoSeleccionado: Alumno?,
    onAlumnoClick: (Alumno) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = alumnos,
            key = { alumno -> alumno.id }
        ) { alumno ->
            AlumnoItemCard(
                alumno = alumno,
                isSelected = alumno.id == alumnoSeleccionado?.id,
                onClick = {
                    onAlumnoClick(alumno)
                }
            )
        }
    }
}

@Composable
fun SeccionListaAlumnos(
    alumnos: List<Alumno>,
    alumnoSeleccionado: Alumno?,
    onAlumnoClick: (Alumno) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Lista de alumnos (${alumnos.size})",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        ListaAlumnos(
            alumnos = alumnos,
            alumnoSeleccionado = alumnoSeleccionado,
            onAlumnoClick = onAlumnoClick
        )
    }
}

@Composable
fun FormularioAlumno(
    alumnoForm: Alumno,
    onChangeAlumno: (Alumno) -> Unit,
    onAddAlumno: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "👤 Añadir nuevo alumno",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(Modifier.weight(1f)) {
                    Text(text = "Nombre:")
                    OutlinedTextField(
                        value = alumnoForm.nombre,
                        onValueChange = { onChangeAlumno(alumnoForm.copy(nombre = it)) },
                        leadingIcon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null) },
                        label = { Text("Nombre") }
                    )
                }

                Column(Modifier.weight(1f)) {
                    Text(text = "Apellido")
                    OutlinedTextField(
                        value = alumnoForm.apellido,
                        onValueChange = { onChangeAlumno(alumnoForm.copy(apellido = it)) },
                        leadingIcon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null) },
                        label = { Text("Apellido") }
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(Modifier.weight(1f)) {
                    Text(text = "Curso:")
                    OutlinedTextField(
                        value = alumnoForm.curso,
                        onValueChange = { onChangeAlumno(alumnoForm.copy(curso = it)) },
                        leadingIcon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null) },
                        label = { Text("Curso") }
                    )
                }

                Column(Modifier.weight(1f)) {
                    Text(text = "Edad")
                    OutlinedTextField(
                        value = alumnoForm.edad,
                        onValueChange = { onChangeAlumno(alumnoForm.copy(edad = it)) },
                        leadingIcon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null) },
                        label = { Text("Edad") }
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(Modifier.weight(1f)) {
                    Text(text = "Email")
                    OutlinedTextField(
                        value = alumnoForm.email,
                        onValueChange = { onChangeAlumno(alumnoForm.copy(email = it)) },
                        leadingIcon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null) },
                        label = { Text("Email") }
                    )
                }
                Column(Modifier.weight(1f)) {
                    Text(text = "Activo")
                    AlumnoCheckbox(
                        checked = alumnoForm.isActive,
                        onCheckedChange = { nuevoValor ->
                            onChangeAlumno(alumnoForm.copy(isActive = nuevoValor))
                        }
                    )
                }
            }

            Button(
                onClick = onAddAlumno,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Añadir alumno")
            }
        }
    }
}

@Preview(showBackground = true, name = "Pantalla escribe")
@Composable
fun PreviewPantallaEscribe() {
    var state by remember {
        mutableStateOf(
            MainState(
                alumnos = listOf(
                    Alumno(
                        id = 1L,
                        nombre = "Juan",
                        apellido = "Pérez",
                        curso = "2º DAM",
                        edad = "18",
                        email = "juan@mail.com",
                        isActive = true
                    )
                )
            )
        )
    }

    ComposeExamCorregidoTheme {
        MainPantallaContent(
            state = state,
            onChangeAlumno = { alumno ->
                state = state.copy(alumno = alumno)
            },
            onSelectAlumno = { alumno ->
                state = state.copy(alumnoSeleccionado = alumno)
            },
            onAddAlumno = {
                val nuevo = state.alumno.copy(id = System.currentTimeMillis())
                state = state.copy(
                    alumnos = state.alumnos + nuevo,
                    alumno = Alumno()
                )
            },
            onBorrarAlumno = {
                val sel = state.alumnoSeleccionado ?: return@MainPantallaContent
                state = state.copy(
                    alumnos = state.alumnos.filter { it.id != sel.id },
                    alumnoSeleccionado = null
                )
            },
            onClearMessages = {
                state = state.copy(error = null, mensaje = null)
            }
        )
    }
}