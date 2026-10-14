package com.example.mvvm.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.R
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.di.AppModule
import com.example.mvvm.domain.model.Politico

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val adapter: PoliticoAdapter by lazy {
        PoliticoAdapter { politicoABorrar ->
            viewModel.handleDelete(politicoABorrar)
        }
    }

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            AppModule.damePresidenteUseCase,
            AppModule.addPoliticoUseCase,
            AppModule.cargarPoliticiosUseCase,
            AppModule.delPoliticoUseCase
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 🟢 TE FALTABA ESTO: Asignar el adapter y el layoutManager al RecyclerView
        setupRecyclerView()

        observarEstado()
        setupEventos()
    }

    private fun setupRecyclerView() {
        binding.listado.adapter = adapter
        binding.listado.layoutManager = LinearLayoutManager(this)
    }

    private fun setupEventos() {
        binding.button.setOnClickListener {
            viewModel.handleDamePresidente()
            viewModel.handleCargaLista()
        }

        binding.buttonAdd.setOnClickListener {
            val p = Politico("De la rua", "peronista")
            viewModel.handleAdd(p)
        }
    }

    private fun observarEstado() {
        viewModel.state.observe(this) { state ->
            state?.presidente?.let {
                binding.textView.text = it
            }

            // 🟢 `.toList()` hace una copia limpia para que ListAdapter detecte los cambios
            state?.politicos?.let { politicos ->
                adapter.submitList(politicos.toList())
            }

            state?.error?.let { error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                viewModel.handleLimpiarError()
            }
        }
    }
}