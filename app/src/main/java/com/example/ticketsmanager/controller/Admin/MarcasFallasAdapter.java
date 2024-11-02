package com.example.ticketsmanager.controller.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.model.Usuario;

import java.util.List;

public class MarcasFallasAdapter extends RecyclerView.Adapter<MarcasFallasAdapter.ViewHolder> {

    private List<Usuario> usuarios;
    private OnUsuarioClickListener listener;

    public MarcasFallasAdapter(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marcas_fallas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.bind(usuario, listener);
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public void setOnUsuarioClickListener(OnUsuarioClickListener listener) {
        this.listener = listener;
    }

    public interface OnUsuarioClickListener {
        void onUsuarioClick(Usuario usuario);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNombreUsuario;
        private TextView txtTipoUsuario;
        private TextView txtMarcas;
        private TextView txtFallas;
        private TextView txtIdUsuario;
        private TextView txtEstadoBloqueo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreUsuario = itemView.findViewById(R.id.txtNombreUsuario);
            txtTipoUsuario = itemView.findViewById(R.id.txtTipoUsuario);
            txtMarcas = itemView.findViewById(R.id.txtMarcas);
            txtFallas = itemView.findViewById(R.id.txtFallas);
            txtIdUsuario = itemView.findViewById(R.id.txtIdUsuario);
            txtEstadoBloqueo = itemView.findViewById(R.id.txtEstadoBloqueo);
        }

        public void bind(Usuario usuario, OnUsuarioClickListener listener) {
            txtNombreUsuario.setText(usuario.getNombreUsuario());
            txtTipoUsuario.setText("Tipo: " + usuario.getTipo());
            txtIdUsuario.setText("ID: " + usuario.getId());
            txtMarcas.setText("Marcas: " + usuario.getMarcas());
            txtFallas.setText("Fallas: " + usuario.getFallas());

            txtEstadoBloqueo.setText(usuario.isBloqueado() ? "Bloqueado" : "Activo");

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onUsuarioClick(usuario);
                }
            });
        }
    }
}
