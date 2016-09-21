package com.github.xfumihiro.react_native_image_to_base64;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import java.util.Map;
import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.provider.MediaStore;
import android.net.Uri;
import java.io.IOException;
import java.io.FileNotFoundException;

public class ImageToBase64Module extends ReactContextBaseJavaModule {
  Context context;

  public ImageToBase64Module(ReactApplicationContext reactContext) {
    super(reactContext);
    this.context = (Context) reactContext;
  }

  @Override
  public String getName() {
    return "RNImageToBase64";
  }

  @ReactMethod
  public void getBase64String(String uri, Callback callback) {
    try {
      Bitmap image = MediaStore.Images.Media.getBitmap(this.context.getContentResolver(), Uri.parse(uri));
      if (image == null) {
        callback.invoke("Failed to decode Bitmap, uri: " + uri);
      } else {
        callback.invoke(null, bitmapToBase64(image));
      }
    } catch (IOException e) {
    }
  }

  private String bitmapToBase64(Bitmap bitmap) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
    byte[] byteArray = byteArrayOutputStream.toByteArray();
    return Base64.encodeToString(byteArray, Base64.DEFAULT);
  }
}
