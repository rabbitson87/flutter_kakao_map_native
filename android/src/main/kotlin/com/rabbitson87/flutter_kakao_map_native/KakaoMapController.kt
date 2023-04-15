package com.rabbitson87.flutter_kakao_map_native

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.os.Bundle
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

class KakaoMapController(context: Context, viewId: Int, args: Map<String, Any>, state: Event?, pluginBinding: FlutterPluginBinding, activityBinding: ActivityPluginBinding)
    : PlatformView, MethodCallHandler, LifecycleEventObserver, ActivityLifecycleCallbacks {
    private var state: Event?
    private var density: Float
    private var channel: MethodChannel
    private var mapView: MapView
    private var lifecycle: Lifecycle
    private var activityBinding: ActivityPluginBinding
    private var pluginBinding: FlutterPluginBinding
    private var isDisposed:Boolean = false

    init {
        this.state = state
        this.density = context.resources.displayMetrics.density
        this.lifecycle = (activityBinding.lifecycle as HiddenLifecycleReference).lifecycle.also { lifecycle = it }
        this.activityBinding = activityBinding
        this.pluginBinding = pluginBinding
        this.mapView = MapView(activityBinding.activity)
        this.channel = MethodChannel(pluginBinding.binaryMessenger, "flutter_kakao_map_native_$viewId")
        this.channel.setMethodCallHandler(this)
        this.lifecycle.addObserver(this)
    }

    private var methods = mutableMapOf<String, (MethodCall, Result) -> Unit>(
        "init" to { call, result ->
            var targetViewId:Int = -1
            if (call.argument<String>("viewId") != null) {
                targetViewId = call.argument<String>("viewId")!! as Int
            }
            result.success(targetViewId == viewId)
        },
        "getPlatformVersion" to { _, result ->
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        },
    )

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (methods[call.method] != null) {
            methods[call.method]!!.invoke(call, result)
        } else {
            result.notImplemented()
        }
    }

    override fun getView(): MapView {
        return mapView
    }

    override fun dispose() {
        if (!isDisposed) {
            isDisposed = true
            channel.setMethodCallHandler(null)
            lifecycle.removeObserver(this)
            activityBinding.activity.application.unregisterActivityLifecycleCallbacks(this)
            
            /// 리스너가 있다면 여기서 초기화
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Event) {
        state = event
    }

    /// ActivityLifecycleCallbacks
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity.hashCode() == activityBinding.activity.hashCode()) {
            state = Event.ON_CREATE
        }
    }

    override fun onActivityStarted(activity: Activity) {
        if (activity.hashCode() == activityBinding.activity.hashCode()) {
            state = Event.ON_START
        }
    }

    override fun onActivityResumed(activity: Activity) {
        if (activity.hashCode() == activityBinding.activity.hashCode()) {
            state = Event.ON_RESUME
        }
    }

    override fun onActivityPaused(activity: Activity) {
        if (activity.hashCode() == activityBinding.activity.hashCode()) {
            state = Event.ON_PAUSE
        }
    }

    override fun onActivityStopped(activity: Activity) {
        if (activity.hashCode() == activityBinding.activity.hashCode()) {
            state = Event.ON_STOP
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (activity.hashCode() == activityBinding.activity.hashCode()) {
            state = Event.ON_DESTROY
            activity.application.unregisterActivityLifecycleCallbacks(this)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, savedInstanceState: Bundle) {}

}
