package comcommon.ui.authorization

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.example.common.components.authorization.Authorization


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(component: Authorization) {
  val state by component.state.subscribeAsState()

  Column(
    modifier = Modifier
      .fillMaxHeight()
      .padding(16.dp),

    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = "Test App",
      modifier = Modifier
        .fillMaxWidth(),
      textAlign = TextAlign.Center,
      fontSize = MaterialTheme.typography.headlineMedium.fontSize
    )

    Button(onClick = {
      component.openWelcomePage()
    }) {
      Text("Change Server")
    }

    Column {
      val focusManager = LocalFocusManager.current

      TextField(
        value = state.username,
        onValueChange = component::updateUserName,
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
          imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onDone = {
          focusManager.moveFocus(FocusDirection.Down)
        }),

        label = {
          Text(text = "Username")
        }
      )

      TextField(
        value = state.password,
        onValueChange = component::updatePassword,
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
          imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
          focusManager.clearFocus()
        }),
        visualTransformation = PasswordVisualTransformation(),

        label = {
          Text(text = "Password")
        }
      )

    }

    Spacer(modifier = Modifier.fillMaxWidth())

    Button(
      modifier = Modifier.fillMaxWidth(),
      enabled = !state.loading,
      onClick = component::doLogin,
    ) {
      Text("Login")
    }
  }
}