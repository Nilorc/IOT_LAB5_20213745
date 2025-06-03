package com.example.mishabitos;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mishabitos.utils.NotificacionUtils;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NotificacionUtils.crearCanales(this);
    }
}
