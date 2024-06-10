package com.example.intelligentcam

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.intelligentcam.navigation.navigation
import com.example.intelligentcam.ui.theme.IntelligentCamTheme
import java.security.Permission

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!checkpermission()){
            ActivityCompat.requestPermissions(
                this, permissionlist,0
            )
        }
        setContent {
            IntelligentCamTheme {
                navigation()

            }
        }
    }

    private fun checkpermission():Boolean{
        return permissionlist.all {
            ContextCompat.checkSelfPermission(
                applicationContext,it)==PackageManager.PERMISSION_GRANTED

        }
    }

    companion object{
        private val permissionlist= arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    }
}

