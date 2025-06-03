package com.example.mishabitos;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mishabitos.databinding.ActivityConfiguracionBinding;
import com.example.mishabitos.receivers.RecordatorioReceiver;

public class ConfiguracionActivity extends AppCompatActivity {

    private ActivityConfiguracionBinding binding;
    private SharedPreferences preferences;
    private static final String PREF_NAME = "app_preferences";
    private static final String KEY_NAME = "nombre_usuario";
    private static final String KEY_MSG = "mensaje_motivacional";
    private static final String KEY_HORAS = "frecuencia_motivacional";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfiguracionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        cargarPreferencias();

        binding.buttonGuardar.setOnClickListener(v -> guardarPreferencias());
    }

    private void cargarPreferencias() {
        String nombre = preferences.getString(KEY_NAME, "");
        String mensaje = preferences.getString(KEY_MSG, "");
        int horas = preferences.getInt(KEY_HORAS, 6); // por defecto 6h

        binding.editTextNombre.setText(nombre);
        binding.editTextMensaje.setText(mensaje);
        binding.editTextHoras.setText(String.valueOf(horas));
    }

    private void guardarPreferencias() {
        String nombre = binding.editTextNombre.getText().toString().trim();
        String mensaje = binding.editTextMensaje.getText().toString().trim();
        String horasStr = binding.editTextHoras.getText().toString().trim();

        if (nombre.isEmpty() || mensaje.isEmpty() || horasStr.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int horas;
        try {
            horas = Integer.parseInt(horasStr);
            if (horas < 1) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Frecuencia inválida", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_NAME, nombre);
        editor.putString(KEY_MSG, mensaje);
        editor.putInt(KEY_HORAS, horas);
        editor.apply();

        programarMotivacion(mensaje, horas); // ✅ programar notificación motivacional

        Toast.makeText(this, "Guardado correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void programarMotivacion(String mensaje, int horas) {
        long intervalo = horas * 60L * 60L * 1000L;
        long inicio = System.currentTimeMillis() + 5000; // comienza en 5 segundos

        Intent intent = new Intent(this, RecordatorioReceiver.class);
        intent.putExtra("nombre", mensaje);
        intent.putExtra("categoria", "motivacion");

        int requestCode = "motivacion".hashCode();
        PendingIntent pi = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                inicio,
                intervalo,
                pi
        );
    }
}
