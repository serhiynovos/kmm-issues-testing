package com.example.android

import com.example.common.App
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.common.components.root.RootComponent

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val root = RootComponent(
      componentContext = defaultComponentContext(),
      storeFactory = DefaultStoreFactory()
    )

    setContent {
      MaterialTheme {
        Surface {
          App(root)
        }
      }
    }
  }
}