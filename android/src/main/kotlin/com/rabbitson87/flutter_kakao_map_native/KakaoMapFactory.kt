package com.rabbitson87.flutter_kakao_map_native

import android.content.Context
import androidx.lifecycle.Lifecycle.Event
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

class KakaoMapFactory(private val state: Event?,
                      private val pluginBinding: FlutterPluginBinding,
                      private val activityBinding: ActivityPluginBinding)
    : PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    override fun create(context: Context?, viewId: Int, args: Any?): PlatformView {
        val params = args as Map<String, Any>
        val builder = KakaoMapBuilder()

        /// flutter 쪽에서 options 로 데이터 보내면 여기서 처리
        ///

        return builder.build(context!!, viewId, params, state, pluginBinding, activityBinding)
    }
}