package com.example.conversorfarenheit.domain.useCases

class ConvertirGradosUseCase {
    fun convertirGrados(gradosStr: String) : Double{
        val celsius = gradosStr.toDoubleOrNull() ?: 0.0
        val fahrenheit = (celsius * 9 / 5) + 32
        return fahrenheit
    }
}