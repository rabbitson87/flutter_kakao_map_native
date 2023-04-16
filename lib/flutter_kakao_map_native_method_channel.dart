import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';

import 'flutter_kakao_map_native_platform_interface.dart';

/// An implementation of [FlutterKakaoMapNativePlatform] that uses method channels.
class MethodChannelFlutterKakaoMapNative extends FlutterKakaoMapNativePlatform {
  /// The method channel used to interact with the native platform.

  final _viewType = 'flutter_kakao_map_native';

  final Map<int, MethodChannel> _channels = {};

  /// Accesses the MethodChannel associated to the passed mapId.
  @visibleForTesting
  @override
  MethodChannel? channel(int viewId) {
    return _channels[viewId];
  }

  @override
  Future<void> init(int viewId) {
    MethodChannel channel;
    if (!_channels.containsKey(viewId)) {
      channel = MethodChannel('flutter_kakao_map_native_$viewId');
      channel.setMethodCallHandler(
              (MethodCall call) => _handleMethodCall(call, viewId));
      _channels[viewId] = channel;
    }
    return _channels[viewId]!.invokeMethod<void>('init', <String, dynamic>{
      'viewId': viewId,
    });
  }

  Future<dynamic> _handleMethodCall(MethodCall call, int viewId) async {
    switch (call.method) {
      default:
        throw MissingPluginException();
    }
  }

  @override
  Widget buildView(Map<String, dynamic> creationParams, Set<Factory<OneSequenceGestureRecognizer>> gestureRecognizers,
      PlatformViewCreatedCallback onPlatformViewCreated) {
    if (defaultTargetPlatform == TargetPlatform.android) {
      return AndroidView(
        viewType: _viewType,
        onPlatformViewCreated: onPlatformViewCreated,
        gestureRecognizers: gestureRecognizers,
        creationParams: creationParams,
        creationParamsCodec: const StandardMessageCodec(),
      );
    } else if (defaultTargetPlatform == TargetPlatform.iOS) {
      return UiKitView(
        viewType: _viewType,
        onPlatformViewCreated: onPlatformViewCreated,
        gestureRecognizers: gestureRecognizers,
        creationParams: creationParams,
        creationParamsCodec: const StandardMessageCodec(),
      );
    }
    return Text('$defaultTargetPlatform is not yet supported by the maps plugin');
  }
}
