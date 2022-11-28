package com.example.common.components.authorization.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent

class AuthorizationStoreFactory(
  private val storeFactory: StoreFactory
) : KoinComponent {

  fun create(): AuthorizationStore = object : AuthorizationStore,
    Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> by storeFactory.create(
      name = "authorization",
      initialState = AuthorizationStore.State(
        username = "test",
        password = "test"
      ),
      reducer = ReducerImpl,
      executorFactory = ::ExecutorImpl
    ) {}

  private class ExecutorImpl :
    CoroutineExecutor<AuthorizationStore.Intent, Nothing, AuthorizationStore.State, Msg, AuthorizationStore.Label>(),
    KoinComponent {

    override fun executeIntent(intent: AuthorizationStore.Intent, getState: () -> AuthorizationStore.State) {
      when (intent) {
        is AuthorizationStore.Intent.UpdateUserName -> dispatch(Msg.UpdateUserName(intent.username))
        is AuthorizationStore.Intent.UpdatePassword -> dispatch(Msg.UpdatePassword(intent.password))
        is AuthorizationStore.Intent.Login -> {
          scope.launch {
            dispatch(Msg.SetLoading(true))
            publish(AuthorizationStore.Label.Authorized)
            dispatch(Msg.SetLoading(false))
          }
        }
      }
    }
  }

  private object ReducerImpl : Reducer<AuthorizationStore.State, Msg> {
    override fun AuthorizationStore.State.reduce(msg: Msg): AuthorizationStore.State = when (msg) {
      is Msg.UpdateUserName -> copy(username = msg.username.trim())
      is Msg.UpdatePassword -> copy(password = msg.password.trim())
      is Msg.SetLoading -> copy(loading = msg.loading)
    }
  }

  private sealed interface Msg {
    class UpdateUserName(val username: String) : Msg
    class UpdatePassword(val password: String) : Msg
    class SetLoading(val loading: Boolean) : Msg
  }
}

@Serializable
data class LoginResponse(
  @SerialName("Status")
  val status: Status
) {
  @Serializable
  data class Status(
    val id: String?
  )
}