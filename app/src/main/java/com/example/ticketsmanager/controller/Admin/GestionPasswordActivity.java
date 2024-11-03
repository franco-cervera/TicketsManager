package com.example.ticketsmanager.controller.Admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.dao.UsuarioDAO;
import com.example.ticketsmanager.database.DatabaseHelper;
import com.example.ticketsmanager.model.Usuario;

import java.util.List;

public class GestionPasswordActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UsuariosAdapter usuariosAdapter;
    private List<Usuario> usuarios;
    private Usuario usuarioSeleccionado;
    private UsuarioDAO usuarioDAO;
    private Button btnBlanquearPass;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_password);

        recyclerView = findViewById(R.id.recyclerViewUsuarios);
        btnBlanquearPass = findViewById(R.id.btnBlanquearPassword);

        dbHelper = new DatabaseHelper(this);

        usuarioDAO = new UsuarioDAO(this);
        usuarios = usuarioDAO.listarTodos();

        usuariosAdapter = new UsuariosAdapter(usuarios);
        recyclerView.setAdapter(usuariosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usuariosAdapter.setOnUsuarioClickListener(usuario -> {
            usuarioSeleccionado = usuario;
            Toast.makeText(this, "Usuario seleccionado: " + usuario.getNombreUsuario(), Toast.LENGTH_SHORT).show();
        });

        btnBlanquearPass.setOnClickListener(view -> {
            if (usuarioSeleccionado != null) {
                int userId = usuarioSeleccionado.getId();
                dbHelper.blanquearPassword(userId);
                usuarios = usuarioDAO.listarTodos();
                usuariosAdapter.notifyDataSetChanged();
                Toast.makeText(GestionPasswordActivity.this, "Contraseña blanqueada para el usuario: " + usuarioSeleccionado.getNombreUsuario(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GestionPasswordActivity.this, "Por favor, selecciona un usuario primero", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
