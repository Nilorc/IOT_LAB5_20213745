package com.example.mishabitos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mishabitos.services.AlmacenamientoNube;

public class SubirFotoActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private String imageUrlActual = null;

    private AlmacenamientoNube almacenamientoNube;

    private ImageView imageView;
    private EditText editTextUrl;
    private Button uploadButton, downloadButton, downloadToDeviceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_foto);

        almacenamientoNube = new AlmacenamientoNube();

        imageView = findViewById(R.id.imageView);
        editTextUrl = findViewById(R.id.editTextUrl);
        uploadButton = findViewById(R.id.uploadButton);
        downloadButton = findViewById(R.id.downloadButton);
        downloadToDeviceButton = findViewById(R.id.downloadToDeviceButton);

        // Seleccionar imagen y subir
        uploadButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });

        // Mostrar imagen desde URL escrita manualmente
        downloadButton.setOnClickListener(v -> {
            String url = editTextUrl.getText().toString().trim();
            if (url.isEmpty()) {
                Toast.makeText(this, "⚠️ Ingresa una URL para mostrar la imagen", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.WEB_URL.matcher(url).matches()) {
                Toast.makeText(this, "❌ La URL no es válida", Toast.LENGTH_SHORT).show();
            } else {
                almacenamientoNube.mostrarImagenEnView(url, imageView);
                imageUrlActual = url;
            }
        });

        // Descargar imagen al dispositivo
        downloadToDeviceButton.setOnClickListener(v -> {
            if (imageUrlActual != null && !imageUrlActual.isEmpty()) {
                almacenamientoNube.descargarImagenDispositivo(imageUrlActual, this, "imagen_descargada");
            } else {
                Toast.makeText(this, "❌ No hay imagen disponible para descargar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                imageView.setImageURI(imageUri);

                almacenamientoNube.uploadImage(imageUri, this, new AlmacenamientoNube.OnUploadListener() {
                    @Override
                    public void onUploadSuccess(String url) {
                        imageUrlActual = url;
                        editTextUrl.setText(url);
                    }
                });
            } else {
                Toast.makeText(this, "❌ No se pudo obtener la imagen seleccionada", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
