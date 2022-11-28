object Versions {
    const val napierVersion = "2.6.1"
    const val decomposeVersion = "1.0.0-beta-01"
    const val mviVersion = "3.0.2"
    const val koin = "3.2.2"
    const val ktorVersion = "2.1.3"
    const val coroutinesVersion = "1.6.4"
    const val kmmSettingsVersion = "1.0.0-RC"
}

object Deps {
    const val Napier = "io.github.aakira:napier:${Versions.napierVersion}"
    const val Settings = "com.russhwolf:multiplatform-settings:${Versions.kmmSettingsVersion}"

    object Decompose {
        const val Decompose = "com.arkivanov.decompose:decompose:${Versions.decomposeVersion}"
        const val DecomposeComposeJetpack = "com.arkivanov.decompose:extensions-compose-jetbrains:${Versions.decomposeVersion}"
    }

    object MviKotlin {
        const val MviKotlin = "com.arkivanov.mvikotlin:mvikotlin:${Versions.mviVersion}"
        const val MviKotlinMain = "com.arkivanov.mvikotlin:mvikotlin-main:${Versions.mviVersion}"
        const val MviKotlinRx = "com.arkivanov.mvikotlin:rx:${Versions.mviVersion}"
        const val MviKotlinCoroutines = "com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:${Versions.mviVersion}"
    }

    object Koin {
        const val Core = "io.insert-koin:koin-core:${Versions.koin}"
        const val Test = "io.insert-koin:koin-test:${Versions.koin}"
        const val Android = "io.insert-koin:koin-android:${Versions.koin}"
    }

    object Ktor {
        const val Core = "io.ktor:ktor-client-core:${Versions.ktorVersion}"
        const val OkHttpClient = "io.ktor:ktor-client-okhttp:${Versions.ktorVersion}"
        const val DarwinClient = "io.ktor:ktor-client-darwin:${Versions.ktorVersion}"
        const val ApacheClient = "io.ktor:ktor-client-apache:${Versions.ktorVersion}"
        const val ContentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktorVersion}"
        const val SerializationKotlinxJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktorVersion}"
    }

    object Coroutines {
        const val Core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
        const val Swing = "org.jetbrains.kotlinx:kotlinx-coroutines-swing:${Versions.coroutinesVersion}"
    }
}