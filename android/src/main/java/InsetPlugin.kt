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
   fun getInsets(invoke: Invoke) {
       val rootView = activity.window.decorView
       ViewCompat.getRootWindowInsets(rootView)?.let { windowInsets ->
           val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
           val result = JSObject()
           result.put("top", systemBars.top)
           result.put("bottom", systemBars.bottom)
           result.put("left", systemBars.left)
           result.put("right", systemBars.right)
           invoke.resolve(result)
       } ?: invoke.reject("Failed to get window insets")
   }
}