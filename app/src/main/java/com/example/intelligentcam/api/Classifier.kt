package com.example.intelligentcam.api

import android.graphics.Bitmap
import com.example.intelligentcam.datamodel.Classification

interface Classifier {
    fun classify(bitmap:Bitmap,rotation:Int):List<Classification>
}