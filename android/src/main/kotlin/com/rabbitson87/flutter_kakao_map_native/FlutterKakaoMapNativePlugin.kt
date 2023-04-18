package com.rabbitson87.flutter_kakao_map_native

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.embedding.engine.plugins.lifecycle.HiddenLifecycleReference

/** FlutterKakaoMapNativePlugin */
class FlutterKakaoMapNativePlugin: FlutterPlugin, ActivityAware,
    LifecycleEventObserver {
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
    Log.e("activity", "onAttachedToEngine")
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
    Log.e("activity", "$event")
    state = event
  }
}