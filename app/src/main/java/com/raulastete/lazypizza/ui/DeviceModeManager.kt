package com.raulastete.lazypizza.ui

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

class DeviceModeManager(
    private val widthSizeClass: WindowWidthSizeClass,
    private val heightSizeClass: WindowHeightSizeClass,
    private val configuration: Configuration
) {

    fun getDeviceMode(): DeviceMode {
        return when {
            configuration.orientation == ORIENTATION_PORTRAIT && widthSizeClass == WindowWidthSizeClass.Compact-> {
                DeviceMode.PhonePortrait
            }
            configuration.orientation == ORIENTATION_LANDSCAPE && heightSizeClass == WindowHeightSizeClass.Compact -> {
                DeviceMode.PhoneLandscape
            }

            configuration.orientation == ORIENTATION_PORTRAIT -> {
                DeviceMode.TabletPortrait
            }
            else -> DeviceMode.TabletLandscape
        }
    }
}


sealed interface DeviceMode {

    object PhonePortrait : DeviceMode
    object PhoneLandscape : DeviceMode
    object TabletPortrait : DeviceMode
    object TabletLandscape : DeviceMode

    fun isPhone() = this is PhonePortrait || this is PhoneLandscape


}
