package com.rabbitson87.flutter_kakao_map_native

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.embedding.engine.plugins.lifecycle.HiddenLifecycleReference
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.platform.PlatformView
import net.daum.mf.map.api.MapView

class KakaoMapController(context: Context, viewId: Int, args: Map<String, Any>, state: Event?, pluginBinding: FlutterPluginBinding, activityBinding: ActivityPluginBinding): PlatformView, MethodCallHandler, LifecycleEventObserver {
    private var state: Event?
    private var density: Float
    private var channel: MethodChannel
    private var view: MapView
    private var lifecycle: Lifecycle
    private var activityBinding: ActivityPluginBinding
    private var pluginBinding: FlutterPluginBinding

    init {
        this.state = state
        this.density = context.resources.displayMetrics.density
        this.lifecycle = (activityBinding.lifecycle as HiddenLifecycleReference).lifecycle.also { lifecycle = it }
        this.activityBinding = activityBinding
        this.pluginBinding = pluginBinding
        this.view = MapView(activityBinding.activity)
        this.channel = MethodChannel(pluginBinding.binaryMessenger, "flutter_kakao_map_native_$viewId")
        this.channel.setMethodCallHandler(this)
        this.lifecycle.addObserver(this)
    }

    private var methods = mutableMapOf<String, (Result) -> Unit>(
        "getPlatformVersion" to { result: Result ->
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        },
    )

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (methods[call.method] != null) {
            methods[call.method]!!.invoke(result)
        } else {
            result.notImplemented()
        }
    }

    override fun getView(): MapView {
        return view
    }

    override fun dispose() {
        channel.setMethodCallHandler(null)
        lifecycle.removeObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Event) {
        state = event
    }
}
