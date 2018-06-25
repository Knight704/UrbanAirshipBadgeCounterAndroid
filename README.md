### About
This is urban airship extension library for either [android native SDK](https://github.com/urbanairship/android-library) or [react-native SDK](https://github.com/urbanairship/react-native-module)
that is adding support for badge counter manipulations from within UA-service similarly like on iOS.
It is based on [ShortcutBadger lib](https://github.com/leolin310148/ShortcutBadger) to support as much launchers as possible, but one should bear in mind that badge counter support
is not guaranteed on every android device.

### Installation
1) Follow instructions on how to include UA SDK into your [react-native](https://github.com/urbanairship/react-native-module) or [android](https://github.com/urbanairship/android-library)
2) TODO: Include setup instructions for lib

### Setup
In your MainApplication.java onCreate method put
```java
BadgeManager.init(context, "MY_EXTRA_KEY");
```
where "MY_EXTRA_KEY" - any string constant, the same would be provided as part of android extra within push payload to UA. See example below
If null will be provided as a key, that default value will be used (which is "com.github.knight704.urbanairshipbadgecounter.BADGE_COUNT").

### Sending push
At the moment library only supports increment/decrement/set logic for badge counter.
To increment/decrement badge counter use "+X"/"-X" format, to set exact value just use "X" (where X is any non-zero number)
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
2) Follow instructions of setting up

### TODO
- Create sample
- Prepare package for usage with gradle (i.e upload to bintray)
- Add react-native wrapper around badge counter (as a separate npm-package)
- Add unit-testing
- Improve extensibility
