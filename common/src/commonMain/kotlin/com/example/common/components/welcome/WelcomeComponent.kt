package com.example.common.components.welcome

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.example.common.asValue
import com.example.common.components.welcome.store.WelcomeStore
import com.example.common.components.welcome.store.WelcomeStoreFactory
import com.example.common.coroutineScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WelcomeComponent(
  componentContext: ComponentContext,
  storeFactory: StoreFactory,
  mainContext: CoroutineContext,
  private val onServerValid: () -> Unit,
) : Welcome, ComponentContext by componentContext {

  private val store = WelcomeStoreFactory(storeFactory).create()

  override val state: Value<Welcome.State> = store.asValue().map {
    Welcome.State(
      serverUrl = it.serverUrl,
      loading = it.loading
    )
  }

  private var _errorMessage: MutableValue<String> = MutableValue("")
  override val errorMessage: Value<String> = _errorMessage

  private val scope = coroutineScope(mainContext + SupervisorJob())

  init {
    scope.launch {
      store.labels.collect {
        when (it) {
          is WelcomeStore.Label.Success -> onServerValid()
          is WelcomeStore.Label.Error -> {
            _errorMessage.value = it.message
          }
        }
      }
    }
  }


  override fun updateServerUrl(url: String) {
    Napier.d {
      "Update Server Url :: $url"
    }
    store.accept(WelcomeStore.Intent.UpdateServerUrl(url))
  }

  override fun onNextClicked() {
    with(state.value) {
      if (serverUrl.isEmpty()) {
        return
      }


      if (!serverUrl.startsWith("http")) {
        updateServerUrl("https://${serverUrl}")
      }


      if (serverUrl.endsWith("/")) {
        updateServerUrl(serverUrl.removeSuffix("/"))
      }
    }

    _errorMessage.value = ""
    store.accept(WelcomeStore.Intent.ValidateServerUrl)
  }
}