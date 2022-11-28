package com.example.common.components.main

import com.arkivanov.decompose.ComponentContext

class MainComponent(componentContext: ComponentContext, private val onOpenAuthorizationPage: () -> Unit) :
    Main, ComponentContext by componentContext {
    override fun openAuthorizationPage() {
        onOpenAuthorizationPage()
    }
}