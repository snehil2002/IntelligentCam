package com.example.intelligentcam.utils

import android.graphics.Bitmap
import java.lang.IllegalArgumentException

fun Bitmap.centercrop(desireh:Int ,desiredw:Int):Bitmap{
    val xstart=(width-desiredw)/2
    val ystart=(height-desireh)/2
    if(xstart<0 || ystart<0 )
        throw IllegalArgumentException("Invalid Arguements")
    return Bitmap.createBitmap(this,xstart,ystart,desiredw,desireh)

}