package com.example.pantallaform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainExamenPantalla()
                }
            }
        }
    }
}

@Composable
fun MainExamenPantalla(modifier: Modifier = Modifier) {
    // Estados con rememberSaveable
    var nombre by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var respuestaRadio by rememberSaveable { mutableStateOf("") }
    var respuestaDesarrollo by rememberSaveable { mutableStateOf("") }

    var checkActivity by rememberSaveable { mutableStateOf(false) }
    var checkViewModel by rememberSaveable { mutableStateOf(false) }
    var checkJs by rememberSaveable { mutableStateOf(false) }
    var checkRepo by rememberSaveable { mutableStateOf(false) }

    val colorPrincipal = Color(0xFF00ACC1) // Turquesa del diseño

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título Principal
            Text(
                text = "Examen de Android",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // --- 1. DATOS DEL ALUMNO ---
            SeccionTitulo(titulo = "1. Datos del Alumno", color = colorPrincipal)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                placeholder = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // --- 2. SELECCIÓN ÚNICA ---
            SeccionTituloConPts("2. Selecciona la respuesta correcta", color = colorPrincipal)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "¿Qué componente se utiliza para crear diseños flexibles?", fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(4.dp))

            val opcionesRadio = listOf("LinearLayout", "ConstraintLayout", "FrameLayout")
            opcionesRadio.forEach { opcion ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = respuestaRadio == opcion,
                        onClick = { respuestaRadio = opcion }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = opcion, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- 3. SELECCIÓN MÚLTIPLE ---
            SeccionTituloConPts("3. Selecciona todas las que apliquen", color = colorPrincipal)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "¿Cuáles son componentes de la arquitectura Android?", fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Checkbox(checked = checkActivity, onCheckedChange = { checkActivity = it })
                    Text(text = "Activity", fontSize = 13.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Checkbox(checked = checkViewModel, onCheckedChange = { checkViewModel = it })
                    Text(text = "ViewModel", fontSize = 13.sp)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Checkbox(checked = checkJs, onCheckedChange = { checkJs = it })
                    Text(text = "JavaScript", fontSize = 13.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Checkbox(checked = checkRepo, onCheckedChange = { checkRepo = it })
                    Text(text = "Repository", fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- 4. PREGUNTA DE DESARROLLO ---
            SeccionTituloConPts("4. Pregunta de desarrollo", color = colorPrincipal)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = respuestaDesarrollo,
                onValueChange = { respuestaDesarrollo = it },
                placeholder = { Text("Explica qué es Dependency Injection") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- BOTÓN INFERIOR ---
            Button(
                onClick = { /* Acción al pulsar enviar */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorPrincipal)
            ) {
                Text(
                    text = "Enviar Examen",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SeccionTitulo(titulo: String, color: Color) {
    Text(
        text = titulo,
        fontWeight = FontWeight.Bold,
        color = color,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SeccionTituloConPts(titulo: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Pts", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(end = 8.dp))
        HorizontalDivider(modifier = Modifier.width(30.dp), thickness = 1.dp, color = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = titulo,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:parent=medium_phone,orientation=portrait",
    showSystemUi = true
)
@Composable
fun ExamenPreview() {
    MaterialTheme {
        MainExamenPantalla()
    }
}