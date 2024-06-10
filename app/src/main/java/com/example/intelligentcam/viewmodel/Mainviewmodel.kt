package com.example.intelligentcam.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Mainviewmodel():ViewModel() {
    val _bitmaps= MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmap=_bitmaps.asStateFlow()
    fun onphototaken(bit:Bitmap){
        Log.d("DDDD","clicked")
        _bitmaps.value+=bit
    }
}