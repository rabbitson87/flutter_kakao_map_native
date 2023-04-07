import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_kakao_map_native/flutter_kakao_map_native.dart';
import 'package:flutter_kakao_map_native/flutter_kakao_map_native_platform_interface.dart';
import 'package:flutter_kakao_map_native/flutter_kakao_map_native_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterKakaoMapNativePlatform
    with MockPlatformInterfaceMixin
    implements FlutterKakaoMapNativePlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterKakaoMapNativePlatform initialPlatform = FlutterKakaoMapNativePlatform.instance;

  test('$MethodChannelFlutterKakaoMapNative is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterKakaoMapNative>());
  });

  test('getPlatformVersion', () async {
    FlutterKakaoMapNative flutterKakaoMapNativePlugin = FlutterKakaoMapNative();
    MockFlutterKakaoMapNativePlatform fakePlatform = MockFlutterKakaoMapNativePlatform();
    FlutterKakaoMapNativePlatform.instance = fakePlatform;

    expect(await flutterKakaoMapNativePlugin.getPlatformVersion(), '42');
  });
}
