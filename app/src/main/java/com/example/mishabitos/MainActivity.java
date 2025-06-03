package com.example.mishabitos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mishabitos.databinding.ActivityMainBinding;
import com.example.mishabitos.receivers.RecordatorioReceiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences preferences;
    private static final String PREF_NAME = "app_preferences";
    private static final String KEY_NAME = "nombre_usuario";
    private static final String KEY_MSG = "mensaje_motivacional";
    private static final int IMAGE_PICK_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Botón: ir a lista de hábitos
        binding.buttonVerHabitos.setOnClickListener(v -> {
            startActivity(new Intent(this, HabitosActivity.class));
        });

        // Botón: ir a configuración
        binding.buttonConfiguraciones.setOnClickListener(v -> {
            startActivity(new Intent(this, ConfiguracionActivity.class));
        });

        // Imagen: seleccionar desde galería
        binding.imageViewPerfil.setOnClickListener(v -> seleccionarImagen());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos(); // Actualiza datos siempre que se regreses
    }

    private void cargarDatos() {
        String nombre = preferences.getString(KEY_NAME, "Usuario");
        String mensaje = preferences.getString(KEY_MSG, "¡Sigue adelante!");

        binding.textViewSaludo.setText("¡Hola, " + nombre + "!");
        binding.textViewMotivacion.setText(mensaje);

        File imageFile = new File(getFilesDir(), "perfil.jpg");
        if (imageFile.exists()) {
            binding.imageViewPerfil.setImageURI(Uri.fromFile(imageFile));
        }
    }

    private void seleccionarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            binding.imageViewPerfil.setImageURI(imageUri);
            guardarImagenEnInterno(imageUri);
        }
    }

    private void guardarImagenEnInterno(Uri uri) {
        try (InputStream in = getContentResolver().openInputStream(uri);
             FileOutputStream out = openFileOutput("perfil.jpg", MODE_PRIVATE)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            Toast.makeText(this, "Imagen guardada", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar imagen", Toast.LENGTH_SHORT).show();
        }
    }



}
