package com.example.common.components.welcome.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class WelcomeStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {

  fun create(): WelcomeStore = object : WelcomeStore,
    Store<WelcomeStore.Intent, WelcomeStore.State, WelcomeStore.Label> by storeFactory.create(
      name = "welcome",
      initialState = WelcomeStore.State(
        serverUrl = "google.com"
      ),
      reducer = ReducerImpl,
      executorFactory = ::ExecutorImpl
    ) {}

  private object ReducerImpl : Reducer<WelcomeStore.State, Msg> {
    override fun WelcomeStore.State.reduce(msg: Msg): WelcomeStore.State = when (msg) {
      is Msg.ServerUrl -> copy(serverUrl = msg.url)
      is Msg.Loading -> copy(loading = msg.loading)
    }
  }


  sealed interface Msg {
    class ServerUrl(val url: String) : Msg
    class Loading(val loading: Boolean) : Msg
  }


  class ExecutorImpl : KoinComponent,
    CoroutineExecutor<WelcomeStore.Intent, Nothing, WelcomeStore.State, Msg, WelcomeStore.Label>(
      mainContext = Dispatchers.Main
    ) {

    override fun executeIntent(
      intent: WelcomeStore.Intent,
      getState: () -> WelcomeStore.State
    ) {
      when (intent) {
        is WelcomeStore.Intent.UpdateServerUrl -> dispatch(Msg.ServerUrl(intent.url))
        is WelcomeStore.Intent.ValidateServerUrl -> validateServerUrl(getState().serverUrl)
      }
    }

    private fun validateServerUrl(serverUrl: String) {

      scope.launch {
        dispatch(Msg.Loading(true))
        publish(WelcomeStore.Label.Success)
        dispatch(Msg.Loading(false))
      }
    }
  }
}