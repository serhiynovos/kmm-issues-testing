package com.example.common.components.welcome.store

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.Store

interface WelcomeStore : Store<WelcomeStore.Intent, WelcomeStore.State, WelcomeStore.Label> {
    sealed interface Intent {
        data class UpdateServerUrl(val url: String) : Intent
        object ValidateServerUrl : Intent
    }

    sealed class Label {
        object Success: Label()
        data class Error(val message: String): Label()
    }

    @Parcelize
    data class State(
        val serverUrl: String,
        val serverValid: Boolean = true,
        val serverOldVersion: Boolean = false,
        val loading: Boolean = false
    ) : Parcelable
}