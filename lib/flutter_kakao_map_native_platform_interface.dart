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

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
