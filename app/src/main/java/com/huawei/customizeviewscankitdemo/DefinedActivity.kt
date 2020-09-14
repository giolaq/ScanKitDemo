package com.huawei.customizeviewscankitdemo

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.hmsscankit.RemoteView
import com.huawei.hms.ml.scan.HmsScan

class DefinedActivity : AppCompatActivity() {

    private lateinit var remoteView: RemoteView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_defined)

        val viewFinderRect = createCustomViewFinder()

        //initialize RemoteView instance, and set calling back for scanning result
        remoteView = RemoteView.Builder().setContext(this).setBoundingBox(viewFinderRect)
            .setFormat(HmsScan.ALL_SCAN_TYPE).build()

        with(remoteView) {
            onCreate(savedInstanceState)
            setOnResultCallback { result ->
                if (result != null && result.isNotEmpty() && result[0] != null &&
                    result[0].getOriginalValue().isNotEmpty()
                ) {
                    val intent = Intent().apply { putExtra(SCAN_RESULT, result[0]) }
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }

        // Add the defined RemoteView to the page layout.
        val params = FrameLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        val frameLayout = findViewById<FrameLayout>(R.id.rim1)
        frameLayout.addView(remoteView, params)
    }

    private fun createCustomViewFinder(): Rect {
        //1.get screen density to caculate viewfinder's rect
        val dm = resources.displayMetrics
        //2.get screen size
        val density = dm.density
        val screenWidth = dm.widthPixels
        val screenHeight = dm.heightPixels
        val scanFrameSize = (SCAN_FRAME_SIZE * density)
        //3.calculate viewfinder's rect,it's in the middle of the layout
        //set scanning area(Optional, rect can be null,If not configure,default is in the center of layout)
        return Rect().apply {
            left = (screenWidth / 2 - scanFrameSize / 2).toInt()
            right = (screenWidth / 2 + scanFrameSize / 2).toInt()
            top = (screenHeight / 2 - scanFrameSize / 2).toInt()
            bottom = (screenHeight / 2 + scanFrameSize / 2).toInt()
        }
    }

    override fun onStart() {
        super.onStart()
        remoteView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        remoteView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        remoteView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        remoteView?.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        remoteView?.onStop()
    }

    companion object {
        private const val TAG = "DefinedActivity"

        //declare the key ,used to get the value returned from scankit
        const val SCAN_RESULT = "scanResult"

        //scan_view_finder width & height is  300dp
        const val SCAN_FRAME_SIZE = 300
    }

}
