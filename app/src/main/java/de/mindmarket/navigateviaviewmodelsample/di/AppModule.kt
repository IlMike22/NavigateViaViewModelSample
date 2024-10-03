package de.mindmarket.navigateviaviewmodelsample.di

import de.mindmarket.navigateviaviewmodelsample.DefaultNavigator
import de.mindmarket.navigateviaviewmodelsample.Destination
import de.mindmarket.navigateviaviewmodelsample.DetailViewModel
import de.mindmarket.navigateviaviewmodelsample.HomeViewModel
import de.mindmarket.navigateviaviewmodelsample.LoginViewModel
import de.mindmarket.navigateviaviewmodelsample.Navigator
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<Navigator> {
        DefaultNavigator(startDestination = Destination.AuthGraph)
    }
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
}