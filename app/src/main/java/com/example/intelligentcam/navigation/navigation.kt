package com.example.intelligentcam.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.intelligentcam.screens.homescreen
import com.example.intelligentcam.screens.splashscreen

@Composable fun navigation(){
    val navcontroller= rememberNavController()
    NavHost(navController = navcontroller, startDestination = Camscreens.SPLASHSCREEN.name ){
        composable(route=Camscreens.HOMESCREEN.name){
            homescreen(navcontroller = navcontroller)
        }
        composable(route=Camscreens.SPLASHSCREEN.name){
            splashscreen(navcontroller = navcontroller)
        }
        
    }
}