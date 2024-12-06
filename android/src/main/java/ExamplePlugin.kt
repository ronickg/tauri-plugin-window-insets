package com.plugin.insets

import android.app.Activity
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import app.tauri.plugin.Invoke
import android.webkit.WebView
import android.util.Log

@InvokeArg
class PingArgs {
  var value: String? = null
}

@TauriPlugin
class ExamplePlugin(private val activity: Activity): Plugin(activity) {
    private val implementation = Example()
  private var webView: WebView? = null

  override fun load(webView: WebView) {
    super.load(webView)
        this.webView = webView

    val rootView = activity.window.decorView
    val windowInsets = ViewCompat.getRootWindowInsets(rootView)

    if (windowInsets == null) {
        return;
    }

    val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

    println("height: " + systemBars.top)
    webView.evaluateJavascript(
    """
    document.documentElement.style.setProperty('--status-bar-height', '${systemBars.top}px');
    console.log('Status bar height set:', getComputedStyle(document.documentElement).getPropertyValue('--status-bar-height'));
    """,
    null
)
    // webView.evaluateJavascript(
    //     "document.documentElement.style.setProperty('--status-bar-height', '${systemBars.top}px')",
    //     null
    // )
  }

    @Command
      fun ping(invoke: Invoke) {
        try {
            val rootView = activity.window.decorView
            val windowInsets = ViewCompat.getRootWindowInsets(rootView)

            if (windowInsets == null) {
                invoke.reject("Window insets not available")
                return
            }

            val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            val gestureInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())

            val systemBarsObj = JSObject()
            systemBarsObj.put("top", systemBars.top)
            systemBarsObj.put("bottom", systemBars.bottom)
            systemBarsObj.put("left", systemBars.left)
            systemBarsObj.put("right", systemBars.right)

            val gestureInsetsObj = JSObject()
            gestureInsetsObj.put("top", gestureInsets.top)
            gestureInsetsObj.put("bottom", gestureInsets.bottom)
            gestureInsetsObj.put("left", gestureInsets.left)
            gestureInsetsObj.put("right", gestureInsets.right)

            val result = JSObject()
            result.put("system_bars", systemBarsObj)  // Changed from systemBars to system_bars
            result.put("gesture_insets", gestureInsetsObj)

            invoke.resolve(result)
        } catch (e: Exception) {
            invoke.reject("Error getting insets: ${e.message}")
        }
    }
    //   fun ping(invoke: Invoke) {
    //     val rootView = activity.window.decorView
    //     ViewCompat.getRootWindowInsets(rootView)?.let { windowInsets ->
    //         val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
    //         val gestureInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())

    //         val result = JSObject()
    //         result.put("systemBars", JSObject().apply {
    //             put("top", systemBars.top)
    //             put("bottom", systemBars.bottom)
    //             put("left", systemBars.left)
    //             put("right", systemBars.right)
    //         })
    //         result.put("gestureInsets", JSObject().apply {
    //             put("top", gestureInsets.top)
    //             put("bottom", gestureInsets.bottom)
    //             put("left", gestureInsets.left)
    //             put("right", gestureInsets.right)
    //         })

    //         invoke.resolve(result)
    //     } ?: invoke.reject("Failed to get window insets")
    // }
    // fun ping(invoke: Invoke) {
    //     val args = invoke.parseArgs(PingArgs::class.java)

    //     val ret = JSObject()
    //     ret.put("value", implementation.pong(args.value ?: "default value :("))
    //     invoke.resolve(ret)
    // }
}
