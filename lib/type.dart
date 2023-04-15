part of flutter_kakao_map_native;

class CameraPosition {
  const CameraPosition({
    required this.target,
    this.zoom = 0.0,
  });

  final MapPoint target;
  final double zoom;

  dynamic toJson() => <String, dynamic>{
    'target': target.toJson(),
    'zoom': zoom,
  };

  static CameraPosition fromJson(dynamic json) => CameraPosition(
      target: MapPoint.fromJson(json['target']),
      zoom: json['zoom'],
    );
}

class MapPoint {
  const MapPoint({
    required this.latitude,
    required this.longitude,
  });

  final double latitude;
  final double longitude;

  dynamic toJson() => <double>[latitude, longitude];

  static MapPoint fromJson(dynamic json) => MapPoint(
      latitude: json['latitude'] ?? 0.0,
      longitude: json['longitude'] ?? 0.0,
    );
}