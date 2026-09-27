package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var scoreTeam1 = 0
    private var scoreTeam2 = 0
    private var faltasScoreTeam1 = 0
    private var faltasScoreTeam2 = 0
    private var isPlaying = false
    private var timerSeconds = 600
    private lateinit var runnable: Runnable
    private val handler = android.os.Handler(android.os.Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnPlay.setOnClickListener {
            if(!isPlaying){
               binding.btnPlay?.text = "Pause"
                isPlaying =true
            }else{
                binding.btnPlay?.text = "Play"
                isPlaying = false
            }
        }

        runnable = object : Runnable {
            override fun run() {
                if (isPlaying){
                    if (timerSeconds > 0){
                        timerSeconds --
                    }else{
                        isPlaying = false
                        binding.btnPlay.text = "Play"
                    }

                    val minutes = timerSeconds / 60
                    val secs = timerSeconds % 60
                    val timeFormatted = String.format("%02d:%02d", minutes, secs)
                    binding.timer.text = timeFormatted
                }
                handler.postDelayed(this,1000)
            }
        }
        handler.post(runnable)

        binding.btnMas1Team1.setOnClickListener {
            scoreTeam1 +=1
            binding.puntosTeam1.text = scoreTeam1.toString()
        }

        binding.btnMas2Team1.setOnClickListener {
            scoreTeam1 +=2
            binding.puntosTeam1.text = scoreTeam1.toString()
        }

        binding.btnMas3Team1.setOnClickListener {
            scoreTeam1 +=3
            binding.puntosTeam1.text = scoreTeam1.toString()
        }

        binding.btnMas1Team2.setOnClickListener {
            scoreTeam2 += 1
            binding.puntosTeam2.text = scoreTeam2.toString()
        }

        binding.btnMas2Team2.setOnClickListener {
            scoreTeam2 += 2
            binding.puntosTeam2.text = scoreTeam2.toString()
        }


        binding.btnMas3Team2.setOnClickListener {
            scoreTeam2 += 3
            binding.puntosTeam2.text = scoreTeam2.toString()
        }

        binding.btnMasFaltaTeam1.setOnClickListener {
            faltasScoreTeam1 += 1
            binding.cantFaltasTeam1.text = faltasScoreTeam1.toString()
        }

        binding.btnMasFaltaTeam2.setOnClickListener {
            faltasScoreTeam2 += 1
            binding.cantFaltasTeam2.text = faltasScoreTeam2.toString()
        }


    }
}