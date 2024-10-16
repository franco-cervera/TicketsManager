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

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.UsuarioViewHolder> {

    private List<Usuario> usuarios;
    private OnUsuarioClickListener onUsuarioClickListener;

    public UsuariosAdapter(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setOnUsuarioClickListener(OnUsuarioClickListener listener) {
        this.onUsuarioClickListener = listener;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.bind(usuario, onUsuarioClickListener);
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombreUsuario, txtTipoUsuario, txtEstadoBloqueo;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreUsuario = itemView.findViewById(R.id.txtNombreUsuario);
            txtTipoUsuario = itemView.findViewById(R.id.txtTipoUsuario);
            txtEstadoBloqueo = itemView.findViewById(R.id.txtEstadoBloqueo);
        }

        public void bind(Usuario usuario, OnUsuarioClickListener listener) {
            txtNombreUsuario.setText(usuario.getNombreUsuario());
            txtTipoUsuario.setText(usuario.getTipo());

            // Mostrar el estado de bloqueo
            txtEstadoBloqueo.setText(usuario.isBloqueado() ? "Bloqueado" : "Activo");


            // Configurar el clic en el elemento
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onUsuarioClick(usuario);
                }
            });
        }
    }

    // Interfaz para el listener de clic
    public interface OnUsuarioClickListener {
        void onUsuarioClick(Usuario usuario);
    }
}
