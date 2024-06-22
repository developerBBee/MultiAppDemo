package com.example.second.ui.destinations

import android.content.Context
import android.content.Intent
import com.example.common.ui.destination.MainDestination
import com.example.second.ui.SecondActivity

object Destinations : MainDestination {
    override fun getMainDestination(context: Context): Intent =
        Intent(context, SecondActivity::class.java)
}