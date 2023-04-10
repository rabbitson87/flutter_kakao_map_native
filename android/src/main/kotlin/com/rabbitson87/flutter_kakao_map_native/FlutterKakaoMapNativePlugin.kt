package com.rabbitson87.flutter_kakao_map_native

import android.app.Activity
import android.util.Log
import androidx.annotation.NonNull
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
class FlutterKakaoMapNativePlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private var pluginBinding: FlutterPluginBinding? = null
  private var activityBinding: ActivityPluginBinding? = null
  private var lifecycle: Lifecycle? = null;
  private var state: Event? = null;

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPluginBinding) {
    pluginBinding = flutterPluginBinding
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_kakao_map_native")
    channel.setMethodCallHandler(this)
  }

  fun updateState(event: Event) {
    Log.e("Activity state: ", event.toString())
            state = event
  }

  override fun onAttachedToActivity(@NonNull binding: ActivityPluginBinding) {
    activityBinding = binding
    lifecycle = (binding.lifecycle as HiddenLifecycleReference).lifecycle
    lifecycle?.addObserver(LifecycleEventObserver { source, event -> updateState(event)
    })
    val activity: Activity = binding.activity
    // Use lifecycle as desired.
  }

  override fun onReattachedToActivityForConfigChanges(@NonNull binding: ActivityPluginBinding) {
    activityBinding = binding
    lifecycle = (binding.lifecycle as HiddenLifecycleReference).lifecycle
      lifecycle?.addObserver(LifecycleEventObserver { source, event -> updateState(event)
      })
      val activity: Activity = binding.activity
      // Use lifecycle as desired.
  }

  override fun onDetachedFromActivity() {
    activityBinding = null
      lifecycle = null
  }

  override fun onDetachedFromActivityForConfigChanges() {
    lifecycle = (activityBinding?.lifecycle as HiddenLifecycleReference).lifecycle
  }

  private var methods = mutableMapOf<String, (Result) -> Unit>(
    "getPlatformVersion" to { result: Result ->
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    }
  )
  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (methods[call.method] != null) {
        methods[call.method]!!.invoke(result)
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
    pluginBinding = null
  }
}