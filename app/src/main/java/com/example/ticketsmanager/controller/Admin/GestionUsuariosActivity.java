package com.example.ticketsmanager.controller.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.controller.RegistroActivity;
import com.example.ticketsmanager.dao.UsuarioDAO;
import com.example.ticketsmanager.model.Usuario;

import java.util.List;


public class GestionUsuariosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UsuariosAdapter usuariosAdapter;
    private List<Usuario> usuarios;
    private Button btnBloquearUsuario, btnDesbloquearUsuario, btnAgregarUsuario;
    private Usuario usuarioSeleccionado;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_usuarios);

        recyclerView = findViewById(R.id.recyclerViewUsuarios);
        btnBloquearUsuario = findViewById(R.id.btnBloquearUsuario);
        btnDesbloquearUsuario = findViewById(R.id.btnDesbloquearUsuario);
        btnAgregarUsuario = findViewById(R.id.btnAgregarUsuario);


        usuarioDAO = new UsuarioDAO(this);
        usuarios = usuarioDAO.listarTodos();


        usuariosAdapter = new UsuariosAdapter(usuarios);
        recyclerView.setAdapter(usuariosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        usuariosAdapter.setOnUsuarioClickListener(usuario -> {
            usuarioSeleccionado = usuario;
            Toast.makeText(this, "Usuario seleccionado: " + usuario.getNombreUsuario(), Toast.LENGTH_SHORT).show();
        });

        // Manejo de clic para bloquear usuario
        btnBloquearUsuario.setOnClickListener(v -> {
            if (usuarioSeleccionado != null) {
                // Verifica si el usuario seleccionado es un Administrador
                if (usuarioSeleccionado.getTipo().equals("Administrador")) {
                    Toast.makeText(this, "No puedes bloquear a un Administrador", Toast.LENGTH_SHORT).show();
                } else {
                    usuarioDAO.bloquearUsuario(usuarioSeleccionado.getId());
                    usuarioSeleccionado.setBloqueado(true);
                    usuariosAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Usuario bloqueado", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Selecciona un usuario para bloquear", Toast.LENGTH_SHORT).show();
            }
        });


        // Manejo de clic para desbloquear usuario
        btnDesbloquearUsuario.setOnClickListener(v -> {
            if (usuarioSeleccionado != null) {
                usuarioDAO.desbloquearUsuario(usuarioSeleccionado.getId());
                usuarioSeleccionado.setBloqueado(false);
                usuariosAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Usuario desbloqueado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Selecciona un usuario para desbloquear", Toast.LENGTH_SHORT).show();
            }
        });


        btnAgregarUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(GestionUsuariosActivity.this, RegistroActivity.class);
            startActivityForResult(intent, 1);
    });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Volver a cargar la lista de usuarios
            usuarios = usuarioDAO.listarTodos();
            usuariosAdapter = new UsuariosAdapter(usuarios);
            recyclerView.setAdapter(usuariosAdapter);


            usuariosAdapter.setOnUsuarioClickListener(usuario -> {
                usuarioSeleccionado = usuario;
                Toast.makeText(this, "Usuario seleccionado: " + usuario.getNombreUsuario(), Toast.LENGTH_SHORT).show();
            });
        }
    }

}

