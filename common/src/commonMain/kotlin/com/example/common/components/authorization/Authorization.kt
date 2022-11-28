package com.example.common.components.authorization

import com.arkivanov.decompose.value.Value

interface Authorization {
  val state: Value<State>
  fun openWelcomePage()

  data class State(
    val username: String,
    val password: String,
    val loading: Boolean
  )

  fun updateUserName(username: String)
  fun updatePassword(password: String)

  fun doLogin()
}