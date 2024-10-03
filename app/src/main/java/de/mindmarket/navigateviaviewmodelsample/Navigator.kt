package de.mindmarket.navigateviaviewmodelsample

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface Navigator {
    val startDestination: Destination
    val navigationActions: Flow<NavigationAction>

    suspend fun navigate(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    suspend fun navigateUp()
}

class DefaultNavigator(
    override val startDestination: Destination
) : Navigator {
    /**
     * Commented approach uses SharedFlow which makes sense when you have several subscribers potentially.
     * But in our case we just will have one subscriber that listens to navigation events. Therefore
     * we will use a Channel instead which behaves the same like SharedFlow but for only one single subscriber.
     * Channel comes also with an integrated buffer by default which caches the emissions when there is no
     * subscriber currently active.
     */
//    private val _navigationActions = MutableSharedFlow<NavigationAction>(
//        replay = 10, // up to 10 emissions will be cached up if there is no subscriber right now
//        extraBufferCapacity = 10
//    )

    //    override val navigationActions = _navigationActions.asSharedFlow()

    private val _navigationActions = Channel<NavigationAction>()
    override val navigationActions = _navigationActions.receiveAsFlow()


    override suspend fun navigate(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        _navigationActions.send(
            NavigationAction.Navigate(
                destination,
                navOptions
            )
        )
    }

    override suspend fun navigateUp() {
        _navigationActions.send(NavigationAction.NavigateUp)
    }
}