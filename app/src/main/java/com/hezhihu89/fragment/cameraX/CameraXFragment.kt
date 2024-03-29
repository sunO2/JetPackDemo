package com.hezhihu89.fragment.cameraX

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import com.bumptech.glide.Glide
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.hezhihu89.fragment.OnFragmentInteractionListener
import com.hezhihu89.kt.click
import com.hezhihu89.kt.fullScreen
import com.hezhihu89.kt.isFalse
import com.hezhihu89.kt.noFullScreen
import com.hezhihu89.jetpackdemo.R
import kotlinx.android.synthetic.main.fragment_camera_x.*
import kotlinx.coroutines.*
import java.io.File
import java.lang.StringBuilder
import com.google.zxing.BarcodeFormat
import com.hezhihu89.utils.DefaultThreadFactory
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CameraXFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CameraXFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CameraXFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var listener: OnFragmentInteractionListener? = null

    private val reader: MultiFormatReader = MultiFormatReader()

    private val logSb = StringBuilder()

    private val threadPoole = ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            LinkedBlockingQueue<Runnable>(), DefaultThreadFactory("拍照处理-CameraX"))

    private lateinit var imageCapture: ImageCapture


    init {
        val map = mapOf<DecodeHintType, Collection<BarcodeFormat>>(
                Pair(DecodeHintType.POSSIBLE_FORMATS, arrayListOf(BarcodeFormat.QR_CODE))
        )
        reader.setHints(map)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_x, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jet_pack_cameraX_log.setOnLongClickListener{
            jet_pack_cameraX_log.text = ""
            logSb.clear()
            false
        }

        jet_pack_camera_view.bindToLifecycle(this)

        val imageAnalysis = ImageAnalysisConfig.Builder()
                .apply{

                }
                .build()
                .let {
                    val imageAnalysis = ImageAnalysis(it)
                    imageAnalysis.setAnalyzer(threadPoole, ImageAnalysis.Analyzer {image, _ ->

                        image.apply {
                            val buffer = planes[0].buffer
                            val data = ByteArray(buffer.remaining())
                            val height = height
                            val width = width
                            buffer.get(data)

                            GlobalScope.launch(Dispatchers.Main) {
                                val result = GlobalScope.async {
                                    val result = image.let { itLet ->

                                        val source = PlanarYUVLuminanceSource(data, width, height, 0, 0, width, height, false)

                                        val bitmap = BinaryBitmap(HybridBinarizer(source))

                                        try {
                                            val result = reader.decode(bitmap)
                                            Log.d(TAG, result.toString())
                                            result
                                        } catch (e: Exception) {
                                            null
                                        }
                                    }
                                    result
                                }
                                result.await()?.apply {
                                    logSb.contains(this.text).isFalse {
                                        logSb.append("解析结果：").appendln(this)
                                        jet_pack_cameraX_log.text = logSb.toString()
                                    }
                                }
                            }
                        }
                    })
                    imageAnalysis
                }
        CameraX.bindToLifecycle(this,imageAnalysis)

        ///拍照
        jet_pack_cameraX_take.click {
            takePhoto()
        }
    }

    /**
     * 拍照
     */
    private fun takePhoto(){
        val photoFilePath = File(context?.cacheDir,"CameraX")
                .apply {
                    if(!exists()){
                        mkdir()
                    }
                }

        jet_pack_camera_view.takePicture(
                File(photoFilePath,"${System.currentTimeMillis()}.jpg"),
                threadPoole,
                object: ImageCapture.OnImageSavedListener {

                    override fun onImageSaved(file: File) {
                        jet_pack_cameraX_take_pre.post{
                            Glide.with(this@CameraXFragment).load(file).into(jet_pack_cameraX_take_pre)
                        }
                    }

                    override fun onError(imageCaptureError: ImageCapture.ImageCaptureError, message: String, cause: Throwable?) {
                    }
                }
        )
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
        fullScreen()

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        noFullScreen()
        CameraX.unbindAll()

    }

    companion object {
        private val TAG:String = CameraXFragment::class.java.simpleName + ": HEZHIHU89"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                CameraXFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
