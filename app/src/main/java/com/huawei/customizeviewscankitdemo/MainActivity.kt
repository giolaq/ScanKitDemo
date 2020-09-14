package com.huawei.customizeviewscankitdemo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.huawei.hms.ml.scan.HmsScan

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun checkPermissions(v: View) {
        // Initialize a list of required permissions to request runtime
        val list = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, list, DEFINED_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return
        } else if (requestCode == DEFINED_CODE) {
            //start your activity for scanning barcode
            this.startActivityForResult(
                Intent(this, DefinedActivity::class.java), REQUEST_CODE_SCAN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK || data == null) {
            return
        } else if (requestCode == REQUEST_CODE_SCAN) {
            // Obtain the return value of HmsScan from the value returned by the onActivityResult method by using ScanUtil.RESULT as the key value.
            val hmsScan = data.getParcelableExtra<HmsScan>(DefinedActivity.SCAN_RESULT)
            if (hmsScan != null) {
                if (!TextUtils.isEmpty(hmsScan.getOriginalValue()))
                    Toast.makeText(this, hmsScan.getOriginalValue(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val DEFINED_CODE = 222
        private const val REQUEST_CODE_SCAN = 0X01
    }
}
