package comcommon.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.common.components.main.Main
import java.util.*


data class MMMessage(
  val uuid: String,
  val title: String,
  var checked: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(component: Main) {
  val selectedItem = remember { mutableStateOf(0) }
  val menuItems = listOf("Songs", "Artists", "Playlists")

  val messages = remember {
    mutableStateListOf<MMMessage>()
  }


  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text("Feed List")
        },
        navigationIcon = {
          IconButton(onClick = {
            component.openAuthorizationPage()
          }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Localized description")
          }
        },
        actions = {
          IconButton(onClick = { /* doSomething() */ }) {
            Icon(
              imageVector = Icons.Filled.AccountBox,
              contentDescription = "Localized description"
            )
          }
        }
      )
    },

    floatingActionButton = {
      ExtendedFloatingActionButton(
        onClick = {
          messages.add(
            MMMessage(
              uuid = UUID.randomUUID().toString(),
              title = "New random mmmessage"
            )
          )
        },
        icon = { Icon(Icons.Filled.Add, "Localized description") },
        text = { Text(text = "New Message") },
      )
    },

    floatingActionButtonPosition = FabPosition.End,

    bottomBar = {
      NavigationBar {
        menuItems.forEachIndexed { index, item ->
          NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = item) },
            label = { Text(item) },
            selected = selectedItem.value == index,
            onClick = { selectedItem.value = index }
          )
        }
      }
    }) {
    Column(
      modifier = Modifier
        .padding(it)
        .fillMaxSize()
    ) {

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1.0f)
      ) {
        LazyColumn(
          modifier = Modifier
            .fillMaxSize()
            .padding(end = 12.dp),

          ) {
          items(messages) {
            ElevatedCard(
              modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ) {

              Row(
                modifier = Modifier
                  .padding(16.dp)
                  .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
              ) {
                Text("Some Value $it", modifier = Modifier.weight(1.0f))

                Switch(
                  checked = it.checked,
                  onCheckedChange = { checked ->
                    it.checked = checked
                  }
                )
              }

            }
          }
        }

        /* VerticalScrollbar(
           adapter = rememberScrollbarAdapter(
             scrollState = scrollBarState
           ),
           modifier = Modifier
             .align(Alignment.CenterEnd)
             .fillMaxHeight()
         )*/
      }
    }
  }
}