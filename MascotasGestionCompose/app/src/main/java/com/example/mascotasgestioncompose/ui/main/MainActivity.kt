package com.example.mascotasgestioncompose.ui.main

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
import androidx.compose.material.icons.automirrored.filled.MenuBook
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
import com.example.mascotasgestioncompose.di.AppModule
import com.example.mascotasgestioncompose.domain.model.Mascota
import com.example.mascotasgestioncompose.ui.theme.MascotasGestionComposeTheme
import com.example.mascotasgestioncompose.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MascotasGestionComposeTheme {
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
            addMascotaUseCase = AppModule.addMascotaUseCase,
            deleteMascotaUseCase = AppModule.deleteMascotaUseCase,
            cargarListaMascotasUseCase = AppModule.cargarListaMascotasUseCase
        )
    )
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainPantallaContent(
        modifier = modifier,
        state = state,
        onChangeMascota = viewModel::onChangeMascota,
        onAddMascota = viewModel::onAddMascota,
        onSelectMascota = viewModel::onSelectMascota,
        onBorrarMascota = viewModel::onDeleteMascota,
        onClearMessages = viewModel::clearMessages
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPantallaContent(
    modifier: Modifier = Modifier,
    state: MainState,
    onChangeMascota: (Mascota) -> Unit,
    onAddMascota: () -> Unit,
    onSelectMascota: (Mascota) -> Unit,
    onBorrarMascota: () -> Unit,
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
                            imageVector = Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Mascotas")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Orange,
                    titleContentColor = Color.White
                )
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp)
        ){
            SeccionListaMascotas(
                state = state,
                onPedidoClick = onSelectMascota,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            FormMascotas(
                state = state,
                onChangeMascota = onChangeMascota,
                onAddMascota = onAddMascota,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Button(
                onClick = onBorrarMascota,
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
                Text("Borrar Mascota seleccionado")
            }

        }

    }

}


@Composable
fun MascotaItemCard(
    mascota: Mascota,
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
                    text = mascota.nombre.take(1).uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Text(text = mascota.nombre, style = MaterialTheme.typography.titleSmall)

                Text(
                    text = "${mascota.especie} - ${mascota.edad}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Dueño : ${mascota.dueño}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                Surface(
                    color = if (mascota.vacunado) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = if (mascota.vacunado) "Vacunado" else "No vacunado",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (mascota.vacunado) Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormMascotas(
    state: MainState,
    onChangeMascota: (Mascota) -> Unit,
    onAddMascota: () -> Unit,
    modifier: Modifier = Modifier
){
    val mascotasSelect = listOf("Perro", "Gato", "Conejo", "Hamster", "Pajaro")
    var expanded by remember { mutableStateOf(false) }

    OutlinedCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Añadir nueva mascota",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Nombre",
                style = MaterialTheme.typography.bodySmall,
            )
            OutlinedTextField(
                value = state.mascota.nombre,
                onValueChange = { onChangeMascota(state.mascota.copy(nombre = it)) },
                label = { Text("Nombre") },
                leadingIcon = { Icon(Icons.Default.MenuBook, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text(
                text = "Especie",
                style = MaterialTheme.typography.bodySmall
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = state.mascota.especie,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Especie") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    mascotasSelect.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                onChangeMascota(state.mascota.copy(especie = opcion))
                                expanded = false
                            }
                        )
                    }
                }
            }
            Text(
                text = "Edad",
                style = MaterialTheme.typography.bodySmall,
            )
            OutlinedTextField(
                value = if (state.mascota.edad == 0) "" else state.mascota.edad.toString(),
                onValueChange = { texto ->
                    onChangeMascota(state.mascota.copy(edad = texto.toIntOrNull() ?: 0))
                },
                label = { Text("Cantidad") },
                leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text(
                text = "Dueño",
                style = MaterialTheme.typography.bodySmall,
            )
            OutlinedTextField(
                value = state.mascota.dueño,
                onValueChange = { onChangeMascota(state.mascota.copy(dueño = it)) },
                label = { Text("Dueño mail") },
                leadingIcon = { Icon(Icons.Default.MenuBook, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )



            PedidoCheckbox(
                checked = state.mascota.vacunado,
                onCheckedChange = { onChangeMascota(state.mascota.copy(vacunado = it)) }
            )
            Button(
                onClick = onAddMascota,
                modifier = Modifier.fillMaxWidth(),


            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Añadir Mascota")

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
        Text(text = if (checked) "vacunado" else "No vacunado")
    }
}

@Composable
fun SeccionListaMascotas(
    state: MainState,
    onPedidoClick: (Mascota) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Lista de pedidos (${state.mascotas.size})",
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
                items = state.mascotas,
                key = { it.id }
            ) { mascota ->
                MascotaItemCard(
                    mascota = mascota,
                    onClick = { onPedidoClick(mascota) }
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MascotasPreview() {
    MascotasGestionComposeTheme {
        MainPantalla()
    }
}