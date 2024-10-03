package de.mindmarket.navigateviaviewmodelsample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import kotlinx.coroutines.launch

class LoginViewModel(
    private val navigator: Navigator
): ViewModel() {
    fun login() {
        viewModelScope.launch {
            navigator.navigate(Destination.HomeGraph)
            navOptions {
                popUpTo(Destination.AuthGraph) {
                    inclusive = true
                }
            }
        }
    }
}