package com.rabbitson87.flutter_kakao_map_native

import androidx.lifecycle.Lifecycle.Event
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory
import io.flutter.

class KakaoMapFactory : PlatformViewFactory(StandardMessageCodec.INSTANCE) {
    init(state: Event?, pluginBinding: FlutterPluginBinding, activityBinding!.activity) {
        println("KakaoMapFactory init")
    }
    override fun create(context: Context?, viewId: Int, args: Any?): PlatformView {
        val params = args as Map<String, Any>
        return KakaoMapView(context!!, viewId, args)
    }
}