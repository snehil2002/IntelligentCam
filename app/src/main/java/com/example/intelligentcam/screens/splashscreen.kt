package com.example.intelligentcam.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.intelligentcam.R
import com.example.intelligentcam.navigation.Camscreens
import kotlinx.coroutines.delay

@Composable fun splashscreen(navcontroller:NavController){

LaunchedEffect(key1 = true) {
    delay(2000)
    navcontroller.navigate(Camscreens.HOMESCREEN.name)

}
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(color = Color.White, modifier = Modifier
                .height(50.dp)
                .width(250.dp), border = BorderStroke(2.dp, color = Color.LightGray), shape = RoundedCornerShape(20.dp)) {
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(text = "IntelligentCam", fontWeight = FontWeight.ExtraBold, color = Color.LightGray,
                        fontSize = 30.sp, fontFamily = FontFamily(Font(R.font.hi))
                    )
                }


            }

        }
        
    }
}