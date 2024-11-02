package com.example.ticketsmanager.controller.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.dao.UsuarioDAO;
import com.example.ticketsmanager.model.Usuario;

import java.util.List;

public class MarcasFallasAdmActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MarcasFallasAdapter usuariosAdapter;
    private List<Usuario> usuarios;
    private UsuarioDAO usuarioDAO;
    private Button btnDesblqTec;
    private Usuario usuarioSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcas_fallas_adm);

        usuarioDAO = new UsuarioDAO(this);

        recyclerView = findViewById(R.id.recyclerViewUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnDesblqTec = findViewById(R.id.btnDesblqTec);

        listarTecnicos();

        usuariosAdapter.setOnUsuarioClickListener(usuario -> {
            usuarioSeleccionado = usuario;
            Toast.makeText(this, "Tecnico seleccionado: " + usuario.getNombreUsuario(), Toast.LENGTH_SHORT).show();
        });

        btnDesblqTec.setOnClickListener(v -> {
            if (usuarioSeleccionado != null) {
                usuarioDAO.desbloquearUsuario(usuarioSeleccionado.getId());
                usuarioSeleccionado.setBloqueado(false);
                usuariosAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Tecnico desbloqueado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Selecciona un tecnico para desbloquear", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listarTecnicos() {
        usuarios = usuarioDAO.listarTecnicos();

        usuariosAdapter = new MarcasFallasAdapter(usuarios);
        recyclerView.setAdapter(usuariosAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            usuarios = usuarioDAO.listarTecnicos();
            usuariosAdapter = new MarcasFallasAdapter(usuarios); // Asegúrate de usar el adaptador correcto
            recyclerView.setAdapter(usuariosAdapter);

            usuariosAdapter.setOnUsuarioClickListener(usuario -> {
                usuarioSeleccionado = usuario;
                Toast.makeText(this, "Tecnico seleccionado: " + usuario.getNombreUsuario(), Toast.LENGTH_SHORT).show();
            });
        }
    }
}
