package com.example.intelligentcam.screens

import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@Composable fun cameraview(modifier: Modifier,
                           controller:LifecycleCameraController){
    val lifecycleowner= LocalLifecycleOwner.current
    AndroidView(factory = { PreviewView(it).apply {
        this.controller=controller
        controller.bindToLifecycle(lifecycleowner)
    } }, modifier = modifier)

}

@Composable fun photobottomsheet(imagebitmaps:List<Bitmap>,modifier: Modifier){
    Surface (modifier = modifier, color = Color.Black){
        if (imagebitmaps.isEmpty()) {
            Column (modifier=Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){
                Text(text = "No Photos Yet", fontSize = 20.sp)
            }
        } else{
            LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp), verticalItemSpacing = 16.dp,
                contentPadding = PaddingValues(16.dp), modifier = Modifier
            ) {
                items(imagebitmaps){
                    Image(bitmap = it.asImageBitmap(), contentDescription = "",modifier=Modifier.clip(
                        RoundedCornerShape(10.dp)
                    ))
                }

            }
        }


    }


}