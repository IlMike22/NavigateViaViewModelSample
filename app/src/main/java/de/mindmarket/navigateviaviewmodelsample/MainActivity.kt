package de.mindmarket.navigateviaviewmodelsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.mindmarket.navigateviaviewmodelsample.ui.theme.NavigateViaViewModelSampleTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigateViaViewModelSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val navigator =
                        koinInject<Navigator>() // inject the wished object here using Koin

                    ObserveAsEvents(flow = navigator.navigationActions) { action ->
                        when (action) {
                            is NavigationAction.Navigate -> navController.navigate(
                                action.destination
                            ) {
                                action.navOptions(this)
                            }

                            NavigationAction.NavigateUp -> navController.navigateUp()
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = navigator.startDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        navigation<Destination.AuthGraph>(
                            startDestination = Destination.LoginScreen
                        ) {
                            composable<Destination.LoginScreen> {
                                val viewModel = koinViewModel<LoginViewModel>()
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Button(onClick = {
                                        viewModel.login()
                                    }) {
                                        Text(text = "Login")
                                    }
                                }
                            }
                        }
                        navigation<Destination.HomeGraph>(
                            startDestination = Destination.HomeScreen
                        ) {
                            composable<Destination.HomeScreen> {
                                val viewModel = koinViewModel<HomeViewModel>()
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Button(onClick = {
                                        viewModel.navigateToDetail(UUID.randomUUID().toString())
                                    }) {
                                        Text(text = "Go to detail screen")
                                    }
                                }
                            }
                            composable<Destination.DetailScreen> {
                                val arguments = it.toRoute<Destination.DetailScreen>()
                                val viewModel = koinViewModel<DetailViewModel>()
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "ID: $arguments.id}")
                                    Button(onClick = { viewModel.goBack() }) {
                                        Text(text = "Go back to Home")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}