package com.plugin.insets

import android.app.Activity
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.tauri.annotation.Command
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import app.tauri.plugin.Invoke

@TauriPlugin
class InsetPlugin(private val activity: Activity): Plugin(activity) {
    @Command
    fun ping(invoke: Invoke) {
        val rootView = activity.window.decorView
        ViewCompat.getRootWindowInsets(rootView)?.let { windowInsets ->
            val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            val gestureInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())

            val result = JSObject()
            result.put("systemBars", JSObject().apply {
                put("top", systemBars.top)
                put("bottom", systemBars.bottom)
                put("left", systemBars.left)
                put("right", systemBars.right)
            })
            result.put("gestureInsets", JSObject().apply {
                put("top", gestureInsets.top)
                put("bottom", gestureInsets.bottom)
                put("left", gestureInsets.left)
                put("right", gestureInsets.right)
            })

            invoke.resolve(result)
        } ?: invoke.reject("Failed to get window insets")
    }
}