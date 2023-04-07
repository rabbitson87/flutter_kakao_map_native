
import 'flutter_kakao_map_native_platform_interface.dart';

class FlutterKakaoMapNative {
  Future<String?> getPlatformVersion() {
    return FlutterKakaoMapNativePlatform.instance.getPlatformVersion();
  }
}
