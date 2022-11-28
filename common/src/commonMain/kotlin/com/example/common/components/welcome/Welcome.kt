package com.example.common.components.welcome

import com.arkivanov.decompose.value.Value

interface Welcome {
    val state: Value<State>
    val errorMessage: Value<String>

    fun updateServerUrl(url: String)

    fun onNextClicked()

    data class State(val serverUrl: String, val serverValid: Boolean = true, val serverOldVersion: Boolean = false, val loading: Boolean = false)
}