package kr.co.naulsnow.arcoredemo.tools;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

public class CameraPermissionHelper {

    /** Check to see we have the necessary permissions for this app. */
    public static boolean hasCameraPermission(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }
}
