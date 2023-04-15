
part of flutter_kakao_map_native;

final FlutterKakaoMapNativePlatform _flutterKakaoMapNativePlatform =
    FlutterKakaoMapNativePlatform.instance;

class KakaoMapController {
  final int viewId;

  KakaoMapController._(
      CameraPosition initialCameraPosition,
      this._kakaoMapState, {
        required this.viewId,
      }) {
    _connectStreams(viewId);
  }

  final _KakaoMapState _kakaoMapState;

  static Future<KakaoMapController> init(
      int viewId,
      CameraPosition initialCameraPosition,
      _KakaoMapState kakaoMapState,
      ) async {
    await _flutterKakaoMapNativePlatform.init(viewId);
    return KakaoMapController._(
      initialCameraPosition,
      kakaoMapState,
      viewId: viewId,
    );
  }

  MethodChannel? get channel => _flutterKakaoMapNativePlatform.channel(viewId);

  void _connectStreams(int viewId) {}
}