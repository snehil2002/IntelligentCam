package com.example.intelligentcam.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat


import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.intelligentcam.R
import com.example.intelligentcam.api.Lianalyzer
import com.example.intelligentcam.api.Tfclassifier
import com.example.intelligentcam.datamodel.Classification
import com.example.intelligentcam.viewmodel.Mainviewmodel
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterialApi::class)
@Composable fun homescreen(navcontroller:NavController){
    val mainviemodel= viewModel<Mainviewmodel>()
    val imagebitmaps by mainviemodel.bitmap.collectAsState()
    val context= LocalContext.current

    val bottomsheetstate= rememberBottomSheetScaffoldState()

    var classifications by remember{
        mutableStateOf(emptyList<Classification>())
    }
    val analyzer= remember {
        Lianalyzer(classifier = Tfclassifier(context = context), onresults = {
            classifications=it
        })
    }
    val controller= remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE or CameraController.IMAGE_ANALYSIS)

        }
    }
    val scope= rememberCoroutineScope()
    var analyzestate by remember {
        mutableStateOf(false)
    }


    BottomSheetScaffold(sheetContent = { photobottomsheet(imagebitmaps = imagebitmaps, modifier = Modifier
        .fillMaxWidth()
        .height(500.dp))}, sheetPeekHeight = 0.dp
    , scaffoldState = bottomsheetstate) {

        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){
                    if (analyzestate){

                        controller.setImageAnalysisAnalyzer(ContextCompat.getMainExecutor(context),analyzer)
                        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center) {
                            Text(text = if (classifications.isEmpty())"Landmark" else classifications[0].name, color = Color.Black, fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(start = 5.dp))

                            Icon(imageVector = Icons.Default.Close, contentDescription = "", tint = Color.Black,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable { analyzestate = false
                                    })
                        }




                    }else{

                        controller.clearImageAnalysisAnalyzer()
                        Surface(modifier = Modifier
                            .fillMaxHeight()
                            .width(180.dp)
                            .padding(5.dp)
                            .clickable { analyzestate = true }, color = Color.Black, shape = RoundedCornerShape(10.dp)) {
                            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "ANALYZE", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                            }


                        }
                    }


                }
                cameraview(modifier = Modifier
                    .fillMaxWidth()
                    .height(650.dp), controller = controller)
                Spacer(modifier = Modifier.height(10.dp))
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                    Icon(painter = painterResource(id = R.drawable.gallery), contentDescription ="gallery", tint = Color.Black, modifier = Modifier
                        .size(60.dp)
                        .padding(10.dp)
                        .clickable {
                            scope.launch {
                                bottomsheetstate.bottomSheetState.expand()
                            }
                        })
                    Icon(painter = painterResource(id = R.drawable.camera), contentDescription ="click", tint = Color.Black, modifier = Modifier
                        .size(100.dp)
                        .padding(10.dp)
                        .clickable {


                            takepicture(
                                controller = controller,
                                context = context,
                                onphototaken = mainviemodel::onphototaken
                            )
                        })
                    Icon(painter = painterResource(id = R.drawable.scam), contentDescription ="switch camera", tint = Color.Black, modifier = Modifier
                        .size(60.dp)
                        .padding(10.dp)
                        .clickable {
                            scope.launch { bottomsheetstate.bottomSheetState.collapse() }
                            if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                                controller.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                            else
                                controller.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                        } )

                }


            }

        }



    }
}

fun takepicture(controller: LifecycleCameraController,onphototaken:(Bitmap)->Unit,
                context:Context){


    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : OnImageCapturedCallback(){
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                Log.d("MMMM","cl")
                val matrix= Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                    postScale(-1f,1f)
                }
                val rotatedbitmap=Bitmap.createBitmap(image.toBitmap(),0,0,image.width,image.height,matrix,true)

                onphototaken(rotatedbitmap)
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.d("EEEE",exception.message.toString())
            }
        }

    )
}