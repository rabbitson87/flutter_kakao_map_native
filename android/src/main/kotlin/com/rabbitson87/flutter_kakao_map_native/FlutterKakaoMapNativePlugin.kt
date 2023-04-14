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
class FlutterKakaoMapNativePlugin: FlutterPlugin, ActivityAware,
    LifecycleEventObserver, ActivityLifecycleCallbacks {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private var pluginBinding: FlutterPluginBinding? = null
  private var activityBinding: ActivityPluginBinding? = null
  private var lifecycle: Lifecycle? = null
  private var state: Event? = null
  private val viewType: String = "flutter_kakao_map_native"

  /// FlutterPlugin
  override fun onAttachedToEngine(flutterPluginBinding: FlutterPluginBinding) {
    pluginBinding = flutterPluginBinding
  }

  override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
    pluginBinding = null
  }

  /// ActivityAware
  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    activityBinding = binding
    lifecycle = (binding.lifecycle as HiddenLifecycleReference).lifecycle
    lifecycle?.addObserver(this)
    pluginBinding
      ?.platformViewRegistry?.registerViewFactory(viewType,
        KakaoMapFactory(state, pluginBinding!!, activityBinding!!)
      )
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
    this.onDetachedFromActivity()
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

  override fun onActivityDestroyed(activity: Activity) {
    if (activity.hashCode() == activityBinding?.activity.hashCode()) {
      state = Event.ON_DESTROY
      activity.application.unregisterActivityLifecycleCallbacks(this)
    }
  }

  override fun onActivitySaveInstanceState(activity: Activity, savedInstanceState: Bundle) {}


}