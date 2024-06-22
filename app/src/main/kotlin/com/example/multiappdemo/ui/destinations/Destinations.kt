package com.example.multiappdemo.ui.destinations

import android.content.Context
import android.content.Intent
import com.example.multiappdemo.ui.MainActivity
import com.example.common.ui.destination.MainDestination

object Destinations : MainDestination {
    override fun getMainDestination(context: Context): Intent =
        Intent(context, MainActivity::class.java)
}