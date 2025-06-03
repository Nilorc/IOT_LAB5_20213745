package com.example.mishabitos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mishabitos.adapters.HabitosAdapter;
import com.example.mishabitos.databinding.ActivityHabitosBinding;
import com.example.mishabitos.models.Habito;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HabitosActivity extends AppCompatActivity {

    private ActivityHabitosBinding binding;
    private ArrayList<Habito> listaHabitos;
    private HabitosAdapter adapter;

    private static final String PREF_NAME = "app_preferences";
    private static final String KEY_HABITOS = "lista_habitos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHabitosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerHabitos.setLayoutManager(new LinearLayoutManager(this));

        binding.botonNuevo.setOnClickListener(v -> {
            startActivity(new Intent(this, NuevoHabitoActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarHabitos();

        if (listaHabitos.isEmpty()) {
            Toast.makeText(this, "No hay hábitos registrados", Toast.LENGTH_SHORT).show();
        }

        adapter = new HabitosAdapter(this, listaHabitos, habito -> {
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar hábito")
                    .setMessage("¿Deseas eliminar este hábito?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        listaHabitos.remove(habito);
                        guardarListaActualizada();
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        binding.recyclerHabitos.setAdapter(adapter);
    }

    private void cargarHabitos() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String jsonHabitos = preferences.getString(KEY_HABITOS, null);

        if (jsonHabitos != null) {
            Type type = new TypeToken<ArrayList<Habito>>() {}.getType();
            listaHabitos = new Gson().fromJson(jsonHabitos, type);
        } else {
            listaHabitos = new ArrayList<>();
        }
    }

    private void guardarListaActualizada() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_HABITOS, new Gson().toJson(listaHabitos));
        editor.apply();
    }
}
