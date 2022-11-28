package comcommon.ui.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.example.common.components.welcome.Welcome


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomePage(component: Welcome) {
  val state by component.state.subscribeAsState()
  val errorMessage by component.errorMessage.subscribeAsState()

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

    Column {
      val focusManager = LocalFocusManager.current

      TextField(
        value = state.serverUrl,
        onValueChange = {
          component.updateServerUrl(it)
        },
        modifier = Modifier.fillMaxWidth(),

        maxLines = 1,
        singleLine = true,

        keyboardOptions = KeyboardOptions.Default.copy(
          imeAction = ImeAction.Done
        ),

        keyboardActions = KeyboardActions(onDone = {
          focusManager.clearFocus()
          component.onNextClicked()
        }),

        isError = errorMessage.isNotEmpty(),
        label = {
          Text(text = "Server Url")
        }
      )

      AnimatedVisibility(visible = errorMessage.isNotEmpty()) {
        Text(text = errorMessage)
      }
    }

    Spacer(modifier = Modifier.fillMaxWidth())

    Button(modifier = Modifier.fillMaxWidth(), onClick = {
      component.onNextClicked()
    }, enabled = state.serverUrl.isNotEmpty() && !state.loading) {
      if (!state.loading) {
        Text(text = "Next")
      }
    }
  }
}