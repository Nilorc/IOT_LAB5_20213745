package com.example.mishabitos;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cloudinary.android.MediaManager;
import com.example.mishabitos.utils.NotificacionUtils;

import java.util.HashMap;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NotificacionUtils.crearCanales(this);

        // Configuración de Cloudinary
        HashMap<String, Object> config = new HashMap<>();
        config.put("cloud_name", "your_cloud_name");  // Tu cloud_name de Cloudinary
        config.put("secure", true);  // Habilitar HTTPS

        // Inicializa Cloudinary
        MediaManager.init(this, config);
    }
}
