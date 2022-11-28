import org.jetbrains.compose.compose

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose")
  id("com.android.library")
  id("kotlin-parcelize")
}

group = "com.example"
version = "1.0-SNAPSHOT"

kotlin {
  android()
  jvm("desktop") {
    compilations.all {
      kotlinOptions.jvmTarget = "11"
    }
  }
  sourceSets {
    val commonMain by getting {
      dependencies {
        api(compose.runtime)
        api(compose.foundation)
        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
        api(compose.material3)

        api(Deps.Decompose.DecomposeComposeJetpack)


        implementation(Deps.Napier)
        implementation(Deps.Decompose.Decompose)
        implementation(Deps.MviKotlin.MviKotlin)
        implementation(Deps.MviKotlin.MviKotlinRx)
        implementation(Deps.MviKotlin.MviKotlinMain)
        implementation(Deps.MviKotlin.MviKotlinCoroutines)
        implementation(Deps.Koin.Core)
        implementation(Deps.Coroutines.Core)
        implementation(Deps.Settings)
        implementation(Deps.Ktor.Core)
        implementation(Deps.Ktor.ContentNegotiation)
        implementation(Deps.Ktor.SerializationKotlinxJson)
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }
    val androidMain by getting {
      dependencies {
        api("androidx.appcompat:appcompat:1.2.0")
        api("androidx.core:core-ktx:1.3.1")
        api("androidx.compose.material3:material3:1.1.0-alpha02")

        implementation(Deps.Ktor.OkHttpClient)
        implementation(Deps.Koin.Android)
      }
    }
    val androidTest by getting {
      dependencies {
        implementation("junit:junit:4.13")
      }
    }
    val desktopMain by getting {
      dependencies {
        api(compose.preview)
        implementation(Deps.Ktor.ApacheClient)
      }
    }
    val desktopTest by getting
  }
}

android {
  compileSdkVersion(33)
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  defaultConfig {
    minSdkVersion(24)
    targetSdkVersion(33)
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}