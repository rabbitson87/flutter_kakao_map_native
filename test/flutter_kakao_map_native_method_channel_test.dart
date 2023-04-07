import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_kakao_map_native/flutter_kakao_map_native_method_channel.dart';

void main() {
  MethodChannelFlutterKakaoMapNative platform = MethodChannelFlutterKakaoMapNative();
  const MethodChannel channel = MethodChannel('flutter_kakao_map_native');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
