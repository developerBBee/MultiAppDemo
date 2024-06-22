package com.example.common.ui.destination

import android.content.Context
import android.content.Intent

/**
 * Main screen destination interface
 */
interface MainDestination {
    fun getMainDestination(context: Context): Intent
}