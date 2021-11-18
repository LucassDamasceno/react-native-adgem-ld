package com.reactnativeadgemld;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.adgem.android.AdGem;
import com.adgem.android.OfferWallCallback;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

@ReactModule(name = AdgemLdModule.NAME)
public class AdgemLdModule extends ReactContextBaseJavaModule {
    public static final String NAME = "AdgemLd";

    public AdgemLdModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }


    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
      reactContext
        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit(eventName, params);
    }

    OfferWallCallback callback = new OfferWallCallback() {
      @Override
      public void onOfferWallStateChanged(int newState) {
        Context context = getReactApplicationContext();
        WritableMap params = Arguments.createMap();
        params.putInt("event", newState);
        sendEvent((ReactContext) context, "ChangedOfferWall", params);
      }

      @Override
      public void onOfferWallReward(int amount) {
        Context context = getReactApplicationContext();
        WritableMap params = Arguments.createMap();
        params.putInt("event", amount);
        sendEvent((ReactContext) context, "RewardOfferWall", params);
      }

      @Override
      public void onOfferWallClosed() {
        Context context = getReactApplicationContext();
        WritableMap params = Arguments.createMap();
        params.putString("event", "closed");
        sendEvent((ReactContext) context, "CloseOfferWall", params);
      }
    };


    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void multiply(int a, int b, Promise promise) {
        promise.resolve(a * b);
    }

    public static native int nativeMultiply(int a, int b);

    @ReactMethod
    public void initializeAdgem(){
      Context context = getReactApplicationContext();
      AdGem adgem = AdGem.get();
      adgem.registerOfferWallCallback(callback);
    }

    @ReactMethod
    public void showOfferWall(){
      Context context = getReactApplicationContext();
      AdGem adgem = AdGem.get();
      adgem.showOfferWall(context);
    }

    @ReactMethod
    public void addListener(String eventName) {
      // Set up any upstream listeners or background tasks as necessary
    }

    @ReactMethod
    public void removeListeners(Integer count) {
      // Remove upstream listeners, stop unnecessary background tasks
    }
}
