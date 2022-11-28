package com.example.common.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.example.common.components.authorization.AuthorizationComponent
import com.example.common.components.main.MainComponent
import com.example.common.components.welcome.WelcomeComponent
import com.russhwolf.settings.Settings
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RootComponent(
  componentContext: ComponentContext,
  private val storeFactory: StoreFactory
) : Root, KoinComponent, ComponentContext by componentContext {
  private val navigation = StackNavigation<Config>()

  private val stack = childStack(
    source = navigation,
    initialConfiguration = Config.Main,
    handleBackButton = true,
    childFactory = ::createChild
  )

  override val childStack: Value<ChildStack<*, Root.Child>>
    get() = stack

  private fun createChild(
    config: Config,
    componentContext: ComponentContext
  ): Root.Child = when (config) {
    is Config.Welcome -> Root.Child.WelcomeChild(
      component = WelcomeComponent(
        componentContext = componentContext,
        storeFactory = storeFactory,
        mainContext = Dispatchers.Main,
        onServerValid = {
          openAuthorizationPage()
        }
      )
    )

    is Config.Main -> Root.Child.MainChild(component = MainComponent(
      componentContext = componentContext,
      onOpenAuthorizationPage = {
        openAuthorizationPage()
      }
    ))

    is Config.Authorization -> Root.Child.AuthorizationChild(
      component = AuthorizationComponent(
        componentContext = componentContext,
        storeFactory = storeFactory,
        mainContext = Dispatchers.Main,
        onOpenWelcomePage = {
          openWelcomePage()
        },
        onAuthorized = {
          openMainPage()
        }
      )
    )
  }

  override fun openAuthorizationPage() {
    navigation.replaceCurrent(Config.Authorization)
  }

  override fun openWelcomePage() {
    navigation.replaceCurrent(Config.Welcome)
  }

  override fun openMainPage() {
    navigation.replaceCurrent(Config.Main)
  }

  private sealed class Config : Parcelable {
    @Parcelize
    object Welcome : Config()

    @Parcelize
    object Authorization : Config()

    @Parcelize
    object Main : Config()
  }
}