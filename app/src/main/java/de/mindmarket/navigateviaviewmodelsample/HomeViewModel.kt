package de.mindmarket.navigateviaviewmodelsample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import kotlinx.coroutines.launch

class HomeViewModel(
    private val navigator: Navigator
): ViewModel() {
    fun navigateToDetail(id:String) {
        viewModelScope.launch {
            navigator.navigate(Destination.DetailScreen(id = id))
        }
    }
}