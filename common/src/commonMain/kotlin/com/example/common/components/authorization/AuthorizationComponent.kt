package com.example.common.components.authorization

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.example.common.asValue
import com.example.common.components.authorization.store.AuthorizationStore
import com.example.common.components.authorization.store.AuthorizationStoreFactory
import com.example.common.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AuthorizationComponent(
  componentContext: ComponentContext,
  storeFactory: StoreFactory,
  mainContext: CoroutineContext,
  private val onOpenWelcomePage: () -> Unit,
  private val onAuthorized: () -> Unit,
) : Authorization, ComponentContext by componentContext {

  private val store = AuthorizationStoreFactory(storeFactory).create()
  override val state: Value<Authorization.State> = store.asValue().map {
    Authorization.State(
      username = it.username,
      password = it.password,
      loading = it.loading
    )
  }

  private val scope = coroutineScope(mainContext + SupervisorJob())

  init {
    scope.launch {
      store.labels.onCompletion {
        println("Completed listening")
      }.collect {
        when(it) {
          is AuthorizationStore.Label.Authorized -> onAuthorized()
          else -> {
            // No Actions for now
          }
        }
      }
    }
  }

  override fun openWelcomePage() {
    onOpenWelcomePage()
  }

  override fun updatePassword(password: String) {
    store.accept(AuthorizationStore.Intent.UpdatePassword(password))
  }

  override fun updateUserName(username: String) {
    store.accept(AuthorizationStore.Intent.UpdateUserName(username))
  }

  override fun doLogin() {
    store.accept(AuthorizationStore.Intent.Login)
  }
}