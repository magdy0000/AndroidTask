package com.example.androidtask.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.media.ExifInterface
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

import com.google.android.material.snackbar.Snackbar
import java.io.IOException

fun Fragment.showToast (massage : Any){
    Toast.makeText(requireContext() ,"$massage" , Toast.LENGTH_LONG ).show()
}


fun View.visibilityInVisible (){
    this.visibility = View.INVISIBLE
}
fun View.visibilityGone (){
    this.visibility = View.GONE
}
fun View.visibilityVisible (){
    this.visibility = View.VISIBLE
}

