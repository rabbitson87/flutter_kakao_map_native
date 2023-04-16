
part of flutter_kakao_map_native;

class KakaoMap extends StatefulWidget {
  const KakaoMap({
  Key? key,
  CameraPosition? initialCameraPosition,
  required this.onMapCreated,
  this.mapType = MapType.standard,
  }) :  _initialCameraPosition = initialCameraPosition ?? const CameraPosition(
    target: MapPoint(
      latitude: 37.566535,
      longitude: 126.97796919999996,
    ),
    zoom: 15.0,
  ),
        super(key: key);

  final CameraPosition _initialCameraPosition;


  /// 지도 생성 완료 후 컨트롤러를 제공합니다.
  final void Function(KakaoMapController) onMapCreated;

  /// 지도 종류
  final MapType mapType;

  @override
  _KakaoMapState createState() => _KakaoMapState();
}

class _KakaoMapState extends State<KakaoMap> {
  /// 지도에 대한 터치 인식을 제공합니다.
  late final Set<Factory<OneSequenceGestureRecognizer>> _gestureRecognizers;
  final Completer<KakaoMapController> _controller =
  Completer<KakaoMapController>();

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _gestureRecognizers = <Factory<OneSequenceGestureRecognizer>>{
      Factory<OneSequenceGestureRecognizer>(
        () => EagerGestureRecognizer(),
      ),
    }.toSet();
  }

  @override
  Widget build(BuildContext context) {
    Map<String, dynamic> creationParams = {
      'initialCameraPosition': widget._initialCameraPosition.toJson(),
    };
    return FlutterKakaoMapNativePlatform.instance.buildView(
      creationParams,
      _gestureRecognizers,
      onPlatformViewCreated,
    );
  }

  Future<void> onPlatformViewCreated(int viewId) async {
    final KakaoMapController controller = await KakaoMapController.init(
      viewId,
      widget._initialCameraPosition,
      this,
    );
    _controller.complete(controller);
    widget.onMapCreated(controller);
  }
}