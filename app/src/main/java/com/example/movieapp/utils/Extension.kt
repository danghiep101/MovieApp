package com.example.movieapp.utils

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext

object Extension {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}