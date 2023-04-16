import 'package:flutter/material.dart';

import 'package:flutter_kakao_map_native/flutter_kakao_map_native.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();

  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  late KakaoMapController _controller;
  bool _isMapCreated = false;


  @override
  void initState() {
    super.initState();
    // initPlatformState();
  }

  void onMapCreated(KakaoMapController controller) {
    print('created');
    setState(() {
      _controller = controller;
      _isMapCreated = true;
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  // Future<void> initPlatformState() async {
  //   String platformVersion;
  //   // Platform messages may fail, so we use a try/catch PlatformException.
  //   // We also handle the message potentially returning null.
  //   try {
  //     platformVersion =
  //         await _flutterKakaoMapNativePlugin.getPlatformVersion() ?? 'Unknown platform version';
  //   } on PlatformException {
  //     platformVersion = 'Failed to get platform version.';
  //   }
  //
  //   // If the widget was removed from the tree while the asynchronous platform
  //   // message was in flight, we want to discard the reply rather than calling
  //   // setState to update our non-existent appearance.
  //   if (!mounted) return;
  //
  //   setState(() {
  //     _platformVersion = platformVersion;
  //   });
  // }

  @override
  Widget build(BuildContext context) {
    final kakaoMap = KakaoMap(
      onMapCreated: onMapCreated,
    );
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: [
              SizedBox(
                width: 300.0,
                height: 200.0,
                child: kakaoMap,
              ),
              Text('Running on: $_platformVersion\n'),
            ],
          ) ,
        ),
      ),
    );
  }
}
