package com.example.kotlin_template

import android.Manifest.permission
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED

private const val REQUEST_COARSE_LOCATION = 99
class MainActivity : AppCompatActivity() {
    val EXTRA_MESSAGE: String = "com.example.myfirstapp.MESSAGE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onResume() {
        super.onResume()
        requestNeededPermissions()
//        setDebugView()
//        DebugFragment.setDebugListener(this)
    }

    fun requestNeededPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) !== PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(permission.ACCESS_COARSE_LOCATION, permission.WRITE_EXTERNAL_STORAGE), REQUEST_COARSE_LOCATION)
            }
        }
    }

}