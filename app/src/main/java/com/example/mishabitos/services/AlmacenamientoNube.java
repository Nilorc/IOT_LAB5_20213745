package com.example.mishabitos.services;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AlmacenamientoNube {

    private Cloudinary cloudinary;

    public AlmacenamientoNube() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "disaxfacf",
                "api_key", "987791488317285",
                "api_secret", "PKYXgGv2yeGfgysHZyyVXpJDKwM"
        ));
    }

    public void uploadImage(Uri imageUri, Context context, OnUploadListener listener) {
        new Thread(() -> {
            try {
                Map<String, Object> options = new HashMap<>();
                options.put("resource_type", "image");
                options.put("folder", "Fotos_lab7"); // Tu carpeta en Cloudinary

                InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
                if (inputStream == null) {
                    throw new IOException("No se pudo abrir el InputStream desde URI.");
                }

                Map uploadResult = cloudinary.uploader().upload(inputStream, options);
                String imageUrl = (String) uploadResult.get("secure_url");

                ((Activity) context).runOnUiThread(() -> {
                    Toast.makeText(context, "Imagen subida: " + imageUrl, Toast.LENGTH_LONG).show();
                    listener.onUploadSuccess(imageUrl);
                });

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Cloudinary", "Error al subir imagen: " + e.getMessage());
                ((Activity) context).runOnUiThread(() ->
                        Toast.makeText(context, "Error al subir imagen", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }


    public void mostrarImagenEnView(String imageUrl, ImageView imageView) {
        Picasso.get().load(imageUrl).into(imageView);
    }

    public void descargarImagenDispositivo(String imageUrl, Context context, String nombreArchivo) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            Toast.makeText(context, "No hay imagen para descargar", Toast.LENGTH_SHORT).show();
            return;
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imageUrl));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nombreArchivo + ".jpg");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public interface OnUploadListener {
        void onUploadSuccess(String url);
    }
}
