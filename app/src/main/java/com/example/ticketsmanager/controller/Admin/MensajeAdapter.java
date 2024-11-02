package com.example.ticketsmanager.controller.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.model.Mensaje;

import java.util.List;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder> {

    private List<Mensaje> mensajeList;
    private OnMensajeClickListener onMensajeClickListener;

    public MensajeAdapter(List<Mensaje> mensajeList, OnMensajeClickListener listener) {
        this.mensajeList = mensajeList;
        this.onMensajeClickListener = listener;
    }

    public void setOnMensajeClickListener(OnMensajeClickListener listener) {
        this.onMensajeClickListener = listener;
    }


    @NonNull
    @Override
    public MensajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensaje, parent, false);
        return new MensajeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MensajeViewHolder holder, int position) {
        Mensaje mensaje = mensajeList.get(position);
        holder.bind(mensaje, onMensajeClickListener);
    }

    @Override
    public int getItemCount() {
        return mensajeList.size();
    }

    static class MensajeViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreUsuario, txtIdUsuario, txtAsunto, txtMensaje, txtEstadoLeido;

        public MensajeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreUsuario = itemView.findViewById(R.id.txtNombreUsuario);
            txtIdUsuario = itemView.findViewById(R.id.txtIdUsuario);
            txtAsunto = itemView.findViewById(R.id.txtAsunto);
            txtMensaje = itemView.findViewById(R.id.txtMensaje);
            txtEstadoLeido = itemView.findViewById(R.id.txtEstadoLeido);

        }

        public void bind(Mensaje mensaje, OnMensajeClickListener listener) {
            txtNombreUsuario.setText(mensaje.getNombreUsuario());
            txtIdUsuario.setText(String.valueOf(mensaje.getIdTecnico()));
            txtAsunto.setText(mensaje.getAsunto());
            txtMensaje.setText(mensaje.getMensaje());
            txtEstadoLeido.setText(mensaje.getEstado());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onMensajeClick(mensaje);
                }
            });
        }
    }

    // Interfaz para el listener de clic
    public interface OnMensajeClickListener {
        void onMensajeClick(Mensaje mensaje);
    }
}
