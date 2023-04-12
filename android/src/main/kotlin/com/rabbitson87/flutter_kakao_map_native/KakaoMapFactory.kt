package com.rabbitson87.flutter_kakao_map_native

import android.content.Context
import androidx.lifecycle.Lifecycle.Event
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

class KakaoMapFactory(private val state: Event?, private val pluginBinding: FlutterPluginBinding,  private val activityBinding: ActivityPluginBinding) : PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    override fun create(context: Context?, viewId: Int, args: Any?): PlatformView {
        val params = args as Map<String, Any>
        return KakaoMapController(context!!, viewId, params, state, pluginBinding, activityBinding)
    }
}