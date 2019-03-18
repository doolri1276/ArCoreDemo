package kr.co.naulsnow.arcoredemo.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import kr.co.naulsnow.arcoredemo.activities.FurnitureSelectActivity;

public class MovingTool {

    public static void goToFurnitureSelectActivityForResult(Activity activity, int requestCode){

        Intent intent = new Intent(activity, FurnitureSelectActivity.class);

        activity.startActivityForResult(intent, requestCode, null);

    }
}
