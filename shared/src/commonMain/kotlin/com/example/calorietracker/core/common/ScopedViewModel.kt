package com.example.calorietracker.core.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import org.koin.compose.viewmodel.koinViewModel

@Composable
inline fun <reified T : ViewModel> koinScopedViewModel(): T {
    val store = remember { ViewModelStore() }
    val owner = remember { object : ViewModelStoreOwner {
        override val viewModelStore = store
    }}
    DisposableEffect(Unit) {
        onDispose { store.clear() }
    }
    return koinViewModel(viewModelStoreOwner = owner)
}
