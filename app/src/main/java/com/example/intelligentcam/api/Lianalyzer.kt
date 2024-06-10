package com.example.intelligentcam.api

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.intelligentcam.datamodel.Classification
import com.example.intelligentcam.utils.centercrop

class Lianalyzer(private val classifier: Classifier,
    private val onresults: (List<Classification>)->Unit):ImageAnalysis.Analyzer {
        var frameskip=0
    override fun analyze(image: ImageProxy) {
        if(frameskip%60==0){
            val bitmap=image.toBitmap()
            val rotation=image.imageInfo.rotationDegrees
            val results=classifier.classify(bitmap = bitmap,rotation)
            onresults(results)
        }
        frameskip++
        image.close()
    }

}