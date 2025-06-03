package com.example.mishabitos;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mishabitos.databinding.ActivityNuevoHabitoBinding;
import com.example.mishabitos.models.Habito;
import com.example.mishabitos.receivers.RecordatorioReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NuevoHabitoActivity extends AppCompatActivity {

    private ActivityNuevoHabitoBinding binding;
    private Calendar calendario = Calendar.getInstance();

    private static final String PREF_NAME = "app_preferences";
    private static final String KEY_HABITOS = "lista_habitos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNuevoHabitoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.categorias_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCategoria.setAdapter(adapter);

        // Fecha y hora
        binding.buttonFechaHora.setOnClickListener(v -> seleccionarFechaHora());

        // Guardar hábito
        binding.buttonGuardar.setOnClickListener(v -> guardarHabito());
    }

    private void seleccionarFechaHora() {
        DatePickerDialog dpd = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendario.set(Calendar.YEAR, year);
                    calendario.set(Calendar.MONTH, month);
                    calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    TimePickerDialog tpd = new TimePickerDialog(this,
                            (view1, hourOfDay, minute) -> {
                                calendario.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendario.set(Calendar.MINUTE, minute);

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                                binding.textFechaHoraSeleccionada.setText(sdf.format(calendario.getTime()));
                            },
                            calendario.get(Calendar.HOUR_OF_DAY),
                            calendario.get(Calendar.MINUTE),
                            true
                    );
                    tpd.show();
                },
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show();
    }

    private void guardarHabito() {
        String nombre = binding.editTextNombre.getText().toString().trim();
        String categoria = binding.spinnerCategoria.getSelectedItem().toString();
        String frecuenciaStr = binding.editTextFrecuencia.getText().toString().trim();
        String fechaHora = binding.textFechaHoraSeleccionada.getText().toString();

        if (nombre.isEmpty() || frecuenciaStr.isEmpty() || fechaHora.contains("no seleccionadas")) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int frecuencia;
        try {
            frecuencia = Integer.parseInt(frecuenciaStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Frecuencia inválida", Toast.LENGTH_SHORT).show();
            return;
        }

        Habito nuevo = new Habito(nombre, categoria, frecuencia, fechaHora);

        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String jsonHabitos = preferences.getString(KEY_HABITOS, null);
        ArrayList<Habito> lista = new ArrayList<>();

        if (jsonHabitos != null) {
            Type type = new TypeToken<ArrayList<Habito>>() {}.getType();
            lista = gson.fromJson(jsonHabitos, type);
        }

        lista.add(nuevo);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_HABITOS, gson.toJson(lista));
        editor.apply();

        programarAlarma(nuevo);  //  NUEVO

        Toast.makeText(this, "Hábito guardado", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void programarAlarma(Habito habito) {
        long intervaloMillis = habito.getFrecuenciaHoras() * 60L * 60L * 1000L;
        long tiempoInicial = calendario.getTimeInMillis();

        Intent intent = new Intent(this, RecordatorioReceiver.class);
        intent.putExtra("nombre", habito.getNombre());
        intent.putExtra("categoria", habito.getCategoria());

        int requestCode = habito.getNombre().hashCode(); // único por hábito
        PendingIntent pi = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                tiempoInicial,
                intervaloMillis,
                pi
        );
    }
}
