package com.example.ctc.base.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Created by ntq on 3/28/18.
 */
object PermissionGrantUtils {
    fun verify(fragment: Fragment, PERMISSIONS: Array<String>, PERMISSIONS_REQUEST_ID: Int): Boolean {
        if (underAPI23())
            return true

        var shouldShowRequest = false
        val pendingPermission = ArrayList<String>()

        for (i in PERMISSIONS.indices) {
            val check = fragment.context?.let { checkSelfPermission(it, PERMISSIONS) } ?:  PackageManager.PERMISSION_DENIED
            if (check != PackageManager.PERMISSION_GRANTED) {
                pendingPermission.add(PERMISSIONS[i])
                if (!shouldShowRequest) {
                    val should = fragment.shouldShowRequestPermissionRationale(PERMISSIONS[i])
                    if (should)
                        shouldShowRequest = true
                }
            }
        }

        val denyPermissionLength = pendingPermission.size
        val denyPermission = arrayOfNulls<String>(denyPermissionLength)
        for (i in 0 until denyPermissionLength) {
            denyPermission[i] = pendingPermission[i]
        }

        if (denyPermissionLength > 0) {
            fragment.requestPermissions(denyPermission, PERMISSIONS_REQUEST_ID)
            return false
        } else {
            return true
        }
    }

    fun verify(activity: Activity?, PERMISSIONS: Array<String>, PERMISSIONS_REQUEST_ID: Int): Boolean {
        if (underAPI23())
            return true

        if (activity == null) {
            return false
        }

        var shouldShowRequest = false
        val pendingPermission = ArrayList<String>()

        for (i in PERMISSIONS.indices) {
            val check = ContextCompat.checkSelfPermission(activity, PERMISSIONS[i])
            if (check != PackageManager.PERMISSION_GRANTED) {
                pendingPermission.add(PERMISSIONS[i])
                if (shouldShowRequest == false) {
                    val should = ActivityCompat.shouldShowRequestPermissionRationale(activity, PERMISSIONS[i])
                    if (should)
                        shouldShowRequest = true
                }
            }
        }

        val denyPermissionLength = pendingPermission.size
        val denyPermission = arrayOfNulls<String>(denyPermissionLength)
        for (i in 0 until denyPermissionLength) {
            denyPermission[i] = pendingPermission[i]
        }

        if (denyPermissionLength > 0) {
            ActivityCompat.requestPermissions(activity, denyPermission, PERMISSIONS_REQUEST_ID)
            return false
        } else {
            return true
        }
    }

    fun underAPI23(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
    }

    fun checkSelfPermission(context: Context?, PERMISSIONS: Array<String>): Boolean {
        if (underAPI23())
            return true
        if (context == null) {
            return false
        }
        val grantedPermission = true

        for (i in PERMISSIONS.indices) {
            val check = ContextCompat.checkSelfPermission(context, PERMISSIONS[i])
            if (check != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return grantedPermission
    }
}