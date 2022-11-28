package com.example.common.components.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.common.components.authorization.Authorization
import com.example.common.components.main.Main
import com.example.common.components.welcome.Welcome


interface Root {
  val childStack: Value<ChildStack<*, Child>>

  fun openAuthorizationPage()
  fun openWelcomePage()
  fun openMainPage()

  sealed class Child {
    class WelcomeChild(val component: Welcome) : Child()
    class AuthorizationChild(val component: Authorization) : Child()
    class MainChild(val component: Main) : Child()
  }


  sealed class Output {
    object HomePage : Output()
    object WelcomePage : Output()
  }
}