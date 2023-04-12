package com.rabbitson87.flutter_kakao_map_native

import android.content.Context
import androidx.lifecycle.Lifecycle.Event
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

class KakaoMapBuilder {
    private lateinit var controller: KakaoMapController
    // need options

    fun build(context: Context?, viewId: Int, args: Map<String, Any>, state: Event?, pluginBinding: FlutterPluginBinding, activityBinding: ActivityPluginBinding): KakaoMapController {
        this.controller = KakaoMapController(context!!, viewId, args, state, pluginBinding, activityBinding)
        return controller
    }
}