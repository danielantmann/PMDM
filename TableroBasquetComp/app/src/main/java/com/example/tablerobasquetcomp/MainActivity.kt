package com.example.tablerobasquetcomp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
                    color = Color.Black // Fondo negro como el marcador
                ) {
                    MarcadorPortraitScreen()
                }
            }
        }
    }
}

@Composable
fun MarcadorPortraitScreen(modifier: Modifier = Modifier) {
    // Estados guardados con rememberSaveable
    var scoreEquipo1 by rememberSaveable { mutableStateOf(34) }
    var scoreEquipo2 by rememberSaveable { mutableStateOf(18) }
    var faltasEquipo1 by rememberSaveable { mutableStateOf(0) }
    var faltasEquipo2 by rememberSaveable { mutableStateOf(0) }
    var isRunning by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Black
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // --- 1. CABECERA: TIEMPO Y PERIODO ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón Play / Pause
                IconButton(
                    onClick = { isRunning = !isRunning },
                    modifier = Modifier
                        .background(Color.White, CircleShape)
                        .size(48.dp)
                ) {
                    Text(text = if (isRunning) "⏸" else "▶", fontSize = 20.sp, color = Color.Black)
                }

                // Reloj estilo digital (Rojo)
                Text(
                    text = "10:00",
                    color = Color.Red,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold
                )

                // Periodo (1.º)
                Text(
                    text = "1.º",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // --- 2. EQUIPO 1 ---
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botones +1, +2, +3 a la izquierda
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        BotonPuntuacion("+1") { scoreEquipo1 += 1 }
                        BotonPuntuacion("+2") { scoreEquipo1 += 2 }
                        BotonPuntuacion("+3") { scoreEquipo1 += 3 }
                    }

                    // Nombre y Puntuación gigante (Verde)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Equipo 1", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = scoreEquipo1.toString(),
                            color = Color.Green,
                            fontSize = 80.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(48.dp))
                }
            }

            // Separador X central
            Text(text = "X", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)

            // --- 3. EQUIPO 2 ---
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botones +1, +2, +3 a la izquierda
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        BotonPuntuacion("+1") { scoreEquipo2 += 1 }
                        BotonPuntuacion("+2") { scoreEquipo2 += 2 }
                        BotonPuntuacion("+3") { scoreEquipo2 += 3 }
                    }

                    // Nombre y Puntuación gigante (Verde)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Equipo 2", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = scoreEquipo2.toString(),
                            color = Color.Green,
                            fontSize = 80.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(48.dp))
                }
            }

            // --- 4. SECCIÓN DE FALTAS ---
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Faltas", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Faltas Equipo 1
                    Button(
                        onClick = { if (faltasEquipo1 > 0) faltasEquipo1-- else faltasEquipo1++ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier.size(36.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(text = "+", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(text = faltasEquipo1.toString(), color = Color.Yellow, fontSize = 32.sp, fontWeight = FontWeight.Bold)

                    Text(text = "X", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)

                    // Faltas Equipo 2
                    Text(text = faltasEquipo2.toString(), color = Color.Yellow, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    Button(
                        onClick = { faltasEquipo2++ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier.size(36.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(text = "+", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // --- 5. BOTONES INFERIORES ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* Acción Editar */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Text(text = "EDITAR", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { /* Acción Comparar */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Text(text = "COMPARAR\nTIR", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun BotonPuntuacion(texto: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = CircleShape,
        modifier = Modifier.size(46.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(text = texto, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Preview(
    showBackground = true,
    device = "spec:parent=medium_phone,orientation=portrait",
    showSystemUi = true
)
@Composable
fun MarcadorPortraitPreview() {
    MaterialTheme {
        MarcadorPortraitScreen()
    }
}