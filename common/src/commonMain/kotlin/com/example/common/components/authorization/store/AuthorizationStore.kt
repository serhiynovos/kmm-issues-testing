package com.example.common.components.authorization.store

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.Store

interface AuthorizationStore: Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> {
  sealed interface Intent {
    class UpdateUserName(val username: String): Intent
    class UpdatePassword(val password: String): Intent
    object Login: Intent
  }

  @Parcelize
  data class State(
    val username: String,
    val password: String,
    val loading: Boolean = false
  ): Parcelable

  sealed class Label {
    object Authorized: Label()
    data class AuthorizationError(val e: Throwable): Label()
  }
}