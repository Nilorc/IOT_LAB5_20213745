package com.example.mishabitos.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotificacionUtils {

    public static final String CANAL_EJERCICIO = "canal_ejercicio";
    public static final String CANAL_ALIMENTACION = "canal_alimentacion";
    public static final String CANAL_SUENO = "canal_sueno";
    public static final String CANAL_LECTURA = "canal_lectura";

    public static void crearCanales(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel canalEjercicio = new NotificationChannel(
                    CANAL_EJERCICIO,
                    "Ejercicio",
                    NotificationManager.IMPORTANCE_HIGH
            );
            canalEjercicio.setDescription("Canal para notificaciones de ejercicio");
            canalEjercicio.enableVibration(true);

            NotificationChannel canalAlimentacion = new NotificationChannel(
                    CANAL_ALIMENTACION,
                    "Alimentación",
                    NotificationManager.IMPORTANCE_HIGH
            );
            canalAlimentacion.setDescription("Canal para notificaciones de alimentación");
            canalAlimentacion.enableVibration(true);

            NotificationChannel canalSueno = new NotificationChannel(
                    CANAL_SUENO,
                    "Sueño",
                    NotificationManager.IMPORTANCE_HIGH
            );
            canalSueno.setDescription("Canal para notificaciones de sueño");
            canalSueno.enableVibration(true);

            NotificationChannel canalLectura = new NotificationChannel(
                    CANAL_LECTURA,
                    "Lectura",
                    NotificationManager.IMPORTANCE_HIGH
            );
            canalLectura.setDescription("Canal para notificaciones de lectura");
            canalLectura.enableVibration(true);

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(canalEjercicio);
            manager.createNotificationChannel(canalAlimentacion);
            manager.createNotificationChannel(canalSueno);
            manager.createNotificationChannel(canalLectura);
        }
    }

    public static String obtenerCanalPorCategoria(String categoria) {
        if (categoria == null) return CANAL_EJERCICIO;
        switch (categoria.toLowerCase()) {
            case "ejercicio":
                return CANAL_EJERCICIO;
            case "alimentación":
            case "alimentacion": // por si no usa tilde
                return CANAL_ALIMENTACION;
            case "sueño":
            case "sueno":
                return CANAL_SUENO;
            case "lectura":
                return CANAL_LECTURA;
            default:
                return CANAL_EJERCICIO;
        }
    }
}
