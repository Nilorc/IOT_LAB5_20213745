package com.example.mishabitos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mishabitos.R;
import com.example.mishabitos.models.Habito;

import java.util.List;

public class HabitosAdapter extends RecyclerView.Adapter<HabitosAdapter.HabitoViewHolder> {

    public interface OnEliminarListener {
        void onEliminar(Habito habito);
    }

    private List<Habito> listaHabitos;
    private Context context;
    private OnEliminarListener listener;

    // Constructor  incluye y asigna listener
    public HabitosAdapter(Context context, List<Habito> listaHabitos, OnEliminarListener listener) {
        this.context = context;
        this.listaHabitos = listaHabitos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HabitoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_habito, parent, false);
        return new HabitoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitoViewHolder holder, int position) {
        Habito h = listaHabitos.get(position);
        holder.textNombre.setText(h.getNombre());
        holder.textCategoria.setText("Categoría: " + h.getCategoria());
        holder.textFrecuencia.setText("Cada " + h.getFrecuenciaHoras() + " horas");
        holder.textInicio.setText("Inicio: " + h.getFechaHoraInicio());

        holder.buttonEliminar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEliminar(h);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaHabitos.size();
    }

    public static class HabitoViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textCategoria, textFrecuencia, textInicio;
        Button buttonEliminar;

        public HabitoViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.textNombre);
            textCategoria = itemView.findViewById(R.id.textCategoria);
            textFrecuencia = itemView.findViewById(R.id.textFrecuencia);
            textInicio = itemView.findViewById(R.id.textInicio);
            buttonEliminar = itemView.findViewById(R.id.buttonEliminar);
        }
    }
}
