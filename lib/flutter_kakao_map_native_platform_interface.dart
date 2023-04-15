import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_kakao_map_native_method_channel.dart';

abstract class FlutterKakaoMapNativePlatform extends PlatformInterface {
  /// Constructs a FlutterKakaoMapNativePlatform.
  FlutterKakaoMapNativePlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterKakaoMapNativePlatform _instance = MethodChannelFlutterKakaoMapNative();

  /// The default instance of [FlutterKakaoMapNativePlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterKakaoMapNative].
  static FlutterKakaoMapNativePlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterKakaoMapNativePlatform] when
  /// they register themselves.
  static set instance(FlutterKakaoMapNativePlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<void> init(int viewId) {
    throw UnimplementedError('init() has not been implemented.');
  }

  Future<MapType> getMapView() {
    throw UnimplementedError('getMapView() has not been implemented.');
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Widget buildView(Map<String, dynamic> creationParams, Set<Factory<OneSequenceGestureRecognizer>> gestureRecognizers,
      PlatformViewCreatedCallback onPlatformViewCreated) {
    throw UnimplementedError('buildView() has not been implemented.');
  }

  channel(int viewId) {}
}

enum CurrentLocationTrackingModeConstants {
  /// 현위치 트랙킹 모드 및 나침반 모드 Off
  trackingModeOff,

  /// 현위치 트랙킹 모드 On, 단말의 위치에 따라 지도 중심이 이동한다. 나침반 모드는 꺼진 상태
  trackingModeOnWithoutHeading,

  /// 현위치 트랙킹 모드 On + 나침반 모드 On, 단말의 위치에 따라 지도 중심이 이동하며 단말의 방향에 따라 지도가 회전한다.(나침반 모드 On)
  trackingModeOnWithHeading,
}

enum MapType {
  /// 기본 지도
  standard,

  /// 위성 지도
  satellite,

  /// 하이브리드 지도
  hybrid,
}
