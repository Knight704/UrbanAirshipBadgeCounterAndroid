### About
This is urban airship extension library for [android native SDK](https://github.com/urbanairship/android-library).
Here is [react-native wrapper](https://github.com/Knight704/RNUrbanAirshipBadgeCounterAndroid) for this library.

The idea was to add support for badge counter manipulations from within UA-service similarly like on iOS.
It is based on [ShortcutBadger lib](https://github.com/leolin310148/ShortcutBadger) to support as much launchers as possible, but one should bear in mind that badge counter support
is not guaranteed on every android device.

### Installation
1) Follow instructions on how to include UA SDK [on SDK page](https://github.com/urbanairship/android-library)
2) Add jitpack to your root `build.gradle`:
```
allprojects {
    repositories {
        ...
        maven { url 'https://www.jitpack.io' }
    }
}
```
3) Add the dependency:
```
dependencies {
    compile 'com.github.knight704:UrbanAirshipBadgeCounterAndroid:0.1.1'
}
```

### Setup
In your `MainApplication.java` onCreate method put this method call before any usage of `BadgeManager` module.
```java
String myExtraKeyConstant = "MY_EXTA_KEY";
BadgeManager.init(context, myExtraKeyConstant);
```
where `myExtraKeyConstant` - any string constant. You should provide badge value under this constant name in extra push payload to UA. See example below.
If null will be provided as a key, then default value will be used (which is "com.github.knight704.urbanairshipbadgecounter.BADGE_COUNT").

### Sending push
Library supports increment/decrement/set logic for badge counter.
To increment/decrement badge counter use "+X"/"-X" format, to set exact value use "X" (where X is any non-zero number).
Badge info for android should be part of extra, i.e
```json
{
  "audience": { ... },
  "device_types": ["android, ios"],
  "notification": {
    "alert": "Wow, badge on android work!",
    "android": {
      "extra": {
        "MY_EXTRA_KEY": "+1"
      }
    }
  }
}
```
> Note that "MY_EXTRA_KEY" that was passed during configuration is placed under `notification.android.extra` payload.
See detailed info about push payload on [UA API Reference](https://docs.urbanairship.com/api/ua/)

### Run sample
1) Edit `sample/src/main/assets/airshipconfig.properties` and put there correct keys from UA console
2) Put google-services.json to `sample/`
3) Optionally edit `MainApplication.java` to use custom badgeExtra key
