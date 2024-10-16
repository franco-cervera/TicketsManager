package com.example.ticketsmanager.controller.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private Usuario usuarioSeleccionado; // Mantén referencia al usuario seleccionado
    private UsuarioDAO usuarioDAO; // Inicializa el DAO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_usuarios);

        recyclerView = findViewById(R.id.recyclerViewUsuarios);
        btnBloquearUsuario = findViewById(R.id.btnBloquearUsuario);
        btnDesbloquearUsuario = findViewById(R.id.btnDesbloquearUsuario);
        btnAgregarUsuario = findViewById(R.id.btnAgregarUsuario);

        // Inicializa tu lista de usuarios
        usuarioDAO = new UsuarioDAO(this); // Asegúrate de inicializar el DAO correctamente
        usuarios = usuarioDAO.listarTodos(); // Obtener usuarios de tu base de datos

        // Configurar el RecyclerView
        usuariosAdapter = new UsuariosAdapter(usuarios);
        recyclerView.setAdapter(usuariosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Establecer el listener para la selección de usuario
        usuariosAdapter.setOnUsuarioClickListener(usuario -> {
            usuarioSeleccionado = usuario; // Asignar el usuario seleccionado
            // Puedes mostrar un mensaje o cambiar el estado visualmente si deseas
            Toast.makeText(this, "Usuario seleccionado: " + usuario.getNombreUsuario(), Toast.LENGTH_SHORT).show();
        });

        // Manejo de clic para bloquear usuario
        btnBloquearUsuario.setOnClickListener(v -> {
            if (usuarioSeleccionado != null) {
                usuarioSeleccionado.setBloqueado(true); // Cambia el estado de bloqueo
                usuarioDAO.actualizar(usuarioSeleccionado); // Actualiza en la base de datos
                usuariosAdapter.notifyDataSetChanged(); // Notifica al adaptador
                Toast.makeText(this, "Usuario bloqueado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Selecciona un usuario para bloquear", Toast.LENGTH_SHORT).show();
            }
        });

        // Manejo de clic para desbloquear usuario
        btnDesbloquearUsuario.setOnClickListener(v -> {
            if (usuarioSeleccionado != null) {
                usuarioSeleccionado.setBloqueado(false); // Cambia el estado de desbloqueo
                usuarioDAO.actualizar(usuarioSeleccionado); // Actualiza en la base de datos
                usuariosAdapter.notifyDataSetChanged(); // Notifica al adaptador
                Toast.makeText(this, "Usuario desbloqueado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Selecciona un usuario para desbloquear", Toast.LENGTH_SHORT).show();
            }
        });

        btnAgregarUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(GestionUsuariosActivity.this, RegistroActivity.class);
            // Si necesitas pasar alguna información, puedes hacerlo aquí
            startActivityForResult(intent, 1);
    });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Volver a cargar la lista de usuarios
            usuarios = usuarioDAO.listarTodos(); // Obtener usuarios de tu base de datos
            usuariosAdapter = new UsuariosAdapter(usuarios);
            recyclerView.setAdapter(usuariosAdapter);

            // Reestablecer el listener para la selección de usuario
            usuariosAdapter.setOnUsuarioClickListener(usuario -> {
                usuarioSeleccionado = usuario; // Asignar el usuario seleccionado
                Toast.makeText(this, "Usuario seleccionado: " + usuario.getNombreUsuario(), Toast.LENGTH_SHORT).show();
            });
        }
    }

}

