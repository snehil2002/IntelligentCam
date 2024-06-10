package com.example.intelligentcam.api

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.Surface

import com.example.intelligentcam.datamodel.Classification
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import org.tensorflow.lite.task.vision.classifier.ImageClassifier.ImageClassifierOptions
import java.lang.Exception

class Tfclassifier(
    private val context: Context,
    private val threshold:Float= 0.5F,
    private val max:Int=1
    ) :Classifier{
    private  var classifier:ImageClassifier?=null
    
    private fun setupclassifier(){
       val baseoptions=BaseOptions.builder().setNumThreads(2).build()
       val options= ImageClassifierOptions.builder().setBaseOptions(baseoptions).setScoreThreshold(threshold).setMaxResults(max).build()
        try {
            classifier=ImageClassifier.createFromFileAndOptions(context, "ltf.tflite",options)

        }catch (e:Exception){
            Log.d("TFEEE","TFEEE")
        }
    }
    override fun classify(bitmap: Bitmap, rotation: Int): List<Classification> {
        if(classifier==null)
            setupclassifier()
        val imageprocesssor= ImageProcessor.Builder().build()
        val tensorimage=imageprocesssor.process(TensorImage.fromBitmap(bitmap))
        val imageprocessingoptions=ImageProcessingOptions.builder().setOrientation(getorientation(rotation)).build()

        val results= classifier?.classify(tensorimage,imageprocessingoptions)

        return results?.flatMap {
            it.categories.map {
                Classification(
                    name = it.displayName
                    , score = it.score
                )
            }
        }?.distinctBy {
            it.name } ?: emptyList()



    }

    private fun getorientation(rotation: Int):ImageProcessingOptions.Orientation{
        return when(rotation){
            Surface.ROTATION_0->ImageProcessingOptions.Orientation.RIGHT_TOP
            Surface.ROTATION_90->ImageProcessingOptions.Orientation.TOP_LEFT
            Surface.ROTATION_180->ImageProcessingOptions.Orientation.RIGHT_BOTTOM
            else->ImageProcessingOptions.Orientation.RIGHT_TOP
        }

    }
}