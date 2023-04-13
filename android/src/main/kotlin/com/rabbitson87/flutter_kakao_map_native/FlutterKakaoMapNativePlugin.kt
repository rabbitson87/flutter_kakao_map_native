package com.rabbitson87.flutter_kakao_map_native

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.embedding.engine.plugins.lifecycle.HiddenLifecycleReference
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FlutterKakaoMapNativePlugin */
class FlutterKakaoMapNativePlugin: FlutterPlugin, MethodCallHandler, ActivityAware,
    LifecycleEventObserver, ActivityLifecycleCallbacks {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private var channel : MethodChannel? = null
  private var pluginBinding: FlutterPluginBinding? = null
  private var activityBinding: ActivityPluginBinding? = null
  private var lifecycle: Lifecycle? = null
  private var state: Event? = null

  /// FlutterPlugin
  override fun onAttachedToEngine(flutterPluginBinding: FlutterPluginBinding) {
    pluginBinding = flutterPluginBinding
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_kakao_map_native")
    channel!!.setMethodCallHandler(this)
  }

  override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
    channel!!.setMethodCallHandler(null)
    channel = null
    pluginBinding = null
  }

  /// MethodCallHandler
  override fun onMethodCall(call: MethodCall, result: Result) {
    if (methods[call.method] != null) {
      methods[call.method]!!.invoke(result)
    } else {
      result.notImplemented()
    }
  }

  private var methods = mutableMapOf<String, (Result) -> Unit>(
    "getPlatformVersion" to { result: Result ->
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    },
  )

  /// ActivityAware
  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    activityBinding = binding
    lifecycle = (binding.lifecycle as HiddenLifecycleReference).lifecycle
    lifecycle?.addObserver(this)
    pluginBinding
      ?.platformViewRegistry?.registerViewFactory("flutter_kakao_map_native", KakaoMapFactory(state, pluginBinding!!, activityBinding!!))
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    (binding.lifecycle as HiddenLifecycleReference).lifecycle.also { lifecycle = it }
    lifecycle?.addObserver(this)
    activityBinding = binding
  }

  override fun onDetachedFromActivity() {
    activityBinding = null
    lifecycle?.removeObserver(this)
    lifecycle = null
  }

  override fun onDetachedFromActivityForConfigChanges() {
    (activityBinding?.lifecycle as HiddenLifecycleReference).lifecycle.also { lifecycle = it }
  }

  /// LifecycleEventObserver
  override fun onStateChanged(source: LifecycleOwner, event: Event) {
    state = event
  }

  /// ActivityLifecycleCallbacks
  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    if (activity.hashCode() == activityBinding?.activity.hashCode()) {
      state = Event.ON_CREATE
    }
  }

  override fun onActivityStarted(activity: Activity) {
    if (activity.hashCode() == activityBinding?.activity.hashCode()) {
      state = Event.ON_START
    }
  }

  override fun onActivityResumed(activity: Activity) {
    if (activity.hashCode() == activityBinding?.activity.hashCode()) {
      state = Event.ON_RESUME
    }
  }

  override fun onActivityPaused(activity: Activity) {
    if (activity.hashCode() == activityBinding?.activity.hashCode()) {
      state = Event.ON_PAUSE
    }
  }

  override fun onActivityStopped(activity: Activity) {
    if (activity.hashCode() == activityBinding?.activity.hashCode()) {
      state = Event.ON_STOP
    }
  }

  override fun onActivitySaveInstanceState(activity: Activity, savedInstanceState: Bundle) {
  }

  override fun onActivityDestroyed(activity: Activity) {
    if (activity.hashCode() == activityBinding?.activity.hashCode()) {
      state = Event.ON_DESTROY
    }
  }

}