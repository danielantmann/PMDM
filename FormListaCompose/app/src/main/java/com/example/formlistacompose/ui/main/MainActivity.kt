package com.example.formlistacompose.ui.main
import androidx.lifecycle.viewmodel.compose.viewModel
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.formlistacompose.domain.model.Alumno
import com.example.formlistacompose.ui.theme.FormListaComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FormListaComposeTheme {
                // Aquí la MainActivity solo llama a la pantalla principal
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
            addAlumnoUseCase = com.example.formlistacompose.di.AppModule.addAlumnoUseCase,
            cargarAlumnosUseCase = com.example.formlistacompose.di.AppModule.cargarAlumnosUseCase,
            deleteAlumnoUseCase = com.example.formlistacompose.di.AppModule.deleteAlumnoUseCase
        )
    )
) {
    // 1. Cogemos el estado del ViewModel
    val state by viewModel.state.collectAsStateWithLifecycle()

    // 2. Llamamos a la pantalla visual y le inyectamos las funciones del ViewModel usando "::"
    MainPantallaContent(
        state = state,
        onChangeAlumno = viewModel::onChangeAlumno,
        onAddAlumno = viewModel::onAddAlumno,
        onBorrarAlumno = viewModel::onDeleteAlumno,
        modifier = modifier
    )
}
// --- PANTALLA PRINCIPAL ---
// Aquí es donde diseñamos toda la interfaz (la barra superior, la columna, etc.)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPantallaContent(
    modifier: Modifier = Modifier,
    state: MainState,                                    // <--- 1. Recibe el estado
    onChangeAlumno: (Alumno) -> Unit,                    // <--- 2. Recibe la función de escribir
    onAddAlumno: () -> Unit,                             // <--- 3. Recibe la función de añadir
    onBorrarAlumno: (Alumno) -> Unit                     // <--- 4. Recibe la función de borrar
) {
    // Variable local para saber qué alumno de la lista ha tocado el usuario para borrarlo
    var alumnoSeleccionado by remember { mutableStateOf<Alumno?>(null) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Gestión de alumnos") },
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
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Pasamos la lista y el evento de selección a la sección de la lista
                SeccionListaAlumnos(
                    state = state,
                    onAlumnoClick = { alumnoTocado ->
                        alumnoSeleccionado = alumnoTocado // Guardamos el alumno que quiere borrar
                    }
                )

                // Pasamos el estado y las acciones al formulario
                FormularioAlumno(
                    state = state,
                    onChangeAlumno = onChangeAlumno,
                    onAddAlumno = onAddAlumno
                )
            }

            // El botón rojo de abajo borra el alumno que esté seleccionado
            Button(
                onClick = {
                    alumnoSeleccionado?.let { alumno ->
                        onBorrarAlumno(alumno)
                        alumnoSeleccionado = null // Limpiamos la selección
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🗑️ Borrar alumno seleccionado")
            }
        }
    }
}

@Composable
fun AlumnoCheckbox(
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
            text = if (checked) "Alumno activo" else "Inactivo"
        )
    }
}
@Composable
fun AlumnoItemCard(
    alumno: Alumno,
    onClick: () -> Unit // Para cuando el usuario haga clic en la tarjeta (por ejemplo, para seleccionarla)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // Permite hacer clic en toda la tarjeta
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
        ) {

            // 1. EL CÍRCULO CON LAS INICIALES (Avatar)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color = MaterialTheme.colorScheme.primaryContainer, shape = androidx.compose.foundation.shape.CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Aquí pintaríamos las iniciales (ej: "LA")
                Text(
                    text = "${alumno.nombre.take(1)}${alumno.apellido.take(1)}".uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            // 2. LOS TEXTOS DEL MEDIO (Nombre, Curso/Edad, Email)
            // Usamos .weight(1f) para que ocupe todo el ancho disponible empujando lo de la derecha
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "${alumno.nombre} ${alumno.apellido}",
                    style = MaterialTheme.typography.titleMedium
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

            // 3. LA PARTE DERECHA (Badge de activo y flechita)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // El badge de "Activo"
                Surface(
                    color = if (alumno.isActive) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = if (alumno.isActive) "Activo" else "Inactivo",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (alumno.isActive) Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                }

                // La flechita de selección ">"
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
fun ListaAlumnos(
    alumnos: List<Alumno>, // Recibe la lista que viene del estado
    onAlumnoClick: (Alumno) -> Unit // Qué pasa cuando tocan un alumno (por ejemplo, para seleccionarlo)
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Deja un pequeño espacio de separación entre tarjeta y tarjeta
    ) {
        // La función "items" recorre la lista solita
        items(
            items = alumnos,
            key = { alumno -> alumno.email } // Clave única para que Compose sepa qué elemento es cuál
        ) { alumno ->
            // Por cada alumno de la lista, pintamos la Card que hicimos antes
            AlumnoItemCard(
                alumno = alumno,
                onClick = {
                    // Aquí mandamos la señal de que han hecho clic en este alumno
                    onAlumnoClick(alumno)
                }
            )
        }
    }
}
@Composable
fun SeccionListaAlumnos(
    state: MainState,
    onAlumnoClick: (Alumno) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Lista de alumnos (${state.alumnos.size})", // Muestra el tamaño real
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        // Llamamos a la LazyColumn pasándole la lista del estado
        ListaAlumnos(
            alumnos = state.alumnos,
            onAlumnoClick = onAlumnoClick
        )
    }
}

@Composable
fun FormularioAlumno(
    state: MainState,
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
                        value = state.alumno.nombre, // Lee del estado
                        onValueChange = { onChangeAlumno(state.alumno.copy(nombre = it)) },
                        label = { Text("Nombre") }
                    )
                }

                Column(Modifier.weight(1f)) {
                    Text(text = "Apellido")
                    OutlinedTextField(
                        value = state.alumno.apellido, // Lee del estado
                        onValueChange = { onChangeAlumno(state.alumno.copy(apellido = it)) },
                        label = { Text("Apellido") }
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(Modifier.weight(1f)) {
                    Text(text = "Curso:")
                    OutlinedTextField(
                        value = state.alumno.curso, // Lee del estado
                        onValueChange = { onChangeAlumno(state.alumno.copy(curso = it)) },
                        label = { Text("Curso") }
                    )
                }

                Column(Modifier.weight(1f)) {
                    Text(text = "Edad")
                    OutlinedTextField(
                        value = state.alumno.edad, // Lee del estado
                        onValueChange = { onChangeAlumno(state.alumno.copy(edad = it)) },
                        label = { Text("Edad") }
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(Modifier.weight(1f)) {
                    Text(text = "Email")
                    OutlinedTextField(
                        value = state.alumno.email, // Lee del estado
                        onValueChange = { onChangeAlumno(state.alumno.copy(email = it)) },
                        label = { Text("Email") }
                    )
                }
                Column(Modifier.weight(1f)) {
                    Text(text = "Activo")
                    // Llamamos a tu componente pasándole el valor del estado y la función para actualizarlo
                    AlumnoCheckbox(
                        checked = state.alumno.isActive,
                        onCheckedChange = { nuevoValor ->
                            onChangeAlumno(state.alumno.copy(isActive = nuevoValor))
                        }
                    )
                }
            }

            Button(
                onClick = onAddAlumno, // Ejecuta la función de añadir del ViewModel
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Añadir alumno")
            }
        }
    }
}
// --- VISTA PREVIA ---
// Ahora podemos previsualizar directamente nuestra pantalla completa
@Preview(showBackground = true)
@Composable
fun MainPantallaPreview() {
    FormListaComposeTheme {
        MainPantalla()
    }
}