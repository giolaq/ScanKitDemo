# Customized View HMS Scan Kit Sample

This project is a sample Android Application to show the power of HMS Scan Kit to scan a QR Code or Bar Code
with a custom view

## Important bits


In the [DefinedActivity.kt](https://github.com/joaobiriba/QuickFaceAnalyzer/blob/master/app/src/main/java/com/huawei/customizedviewscankitdemo/DefinedActivity.kt) 
there is all the magic to perform a code scan with a custom view

just build a RemoteView with the bounding box set up as you want.
If you prefer to scan only a particular format of codes check the options in the official [documentation](https://developer.huawei.com/consumer/en/doc/development/HMSCore-References-V5/constant-values-0000001050817323-V5)
```kotlin
  val viewFinderRect = createCustomViewFinder()

        //initialize RemoteView instance, and set calling back for scanning result
        remoteView = RemoteView.Builder().setContext(this).setBoundingBox(viewFinderRect)
            .setFormat(HmsScan.ALL_SCAN_TYPE).build()

        with(remoteView) {
            onCreate(savedInstanceState)
            setOnResultCallback { result ->
                if (result != null && result.isNotEmpty() && result[0] != null &&
                    result[0].getOriginalValue().isNotEmpty()
                ) {
                    val intent = Intent().apply { putExtra(SCAN_RESULT, result[0]) }
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        } 
```
## Usage

Open the project with Android Studio 4.0 and run it or alternatively use gradle directly

Tap on the button

Enable the permission requested by the system

Scan a QR or a bar Code

## Use it!

You are encouraged to try it out and study and hack every parts to achieve your own target
