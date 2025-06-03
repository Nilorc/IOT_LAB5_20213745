package com.example.mishabitos.receivers;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mishabitos.HabitosActivity;
import com.example.mishabitos.R;
import com.example.mishabitos.utils.NotificacionUtils;

public class RecordatorioReceiver extends BroadcastReceiver {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Recordatorio recibido", Toast.LENGTH_SHORT).show();
        String nombre = intent.getStringExtra("nombre");
        String categoria = intent.getStringExtra("categoria");

        if (categoria == null) categoria = "ejercicio";

        // Si es mensaje motivacional, usa canal lectura y cambia título
        boolean esMotivacional = "motivacion".equalsIgnoreCase(categoria);
        String canal = NotificacionUtils.obtenerCanalPorCategoria(categoria);

        String titulo = esMotivacional ? "Mensaje motivacional" : "Recordatorio de hábito";
        String contenido = esMotivacional ? nombre : "Realizar: " + nombre;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, canal)
                .setSmallIcon(R.drawable.ic_recordatorio)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_recordatorio))
                .setContentTitle(titulo)
                .setContentText(contenido)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Acción al hacer clic: abrir lista de hábitos
        Intent i = new Intent(context, HabitosActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pi);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
