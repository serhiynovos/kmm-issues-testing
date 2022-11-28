package com.example.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.example.common.components.root.Root
import com.example.common.components.root.RootComponent
import comcommon.ui.authorization.LoginPage
import comcommon.ui.main.MainPage
import comcommon.ui.welcome.WelcomePage

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun App(root: RootComponent) {
  val childStack by root.childStack.subscribeAsState()

  Children(
    stack = childStack,
    modifier = Modifier.fillMaxSize(),
    animation = stackAnimation(fade())
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
    ) {
      when (val child = it.instance) {
        is Root.Child.WelcomeChild -> WelcomePage(child.component)
        is Root.Child.MainChild -> MainPage(child.component)
        is Root.Child.AuthorizationChild -> LoginPage(child.component)
      }
    }
  }
}
