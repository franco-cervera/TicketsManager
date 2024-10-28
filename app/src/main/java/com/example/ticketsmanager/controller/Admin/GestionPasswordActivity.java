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
    private Usuario usuarioSeleccionado; // Mantén referencia al usuario seleccionado
    private UsuarioDAO usuarioDAO; // Inicializa el DAO
    private Button btnBlanquearPass;
    private EditText idUsuario;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_password);

        recyclerView = findViewById(R.id.recyclerViewUsuarios);
        btnBlanquearPass = findViewById(R.id.btnBlanquearPassword);
        idUsuario = findViewById(R.id.edt_idUsuario_BlanqueoPassword);

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
            String userIdStr = idUsuario.getText().toString();
            if (!userIdStr.isEmpty()) {
                try {
                    int userId = Integer.parseInt(userIdStr);
                    Usuario usuario = usuarioDAO.listar(userId);
                    if (usuario != null) {
                        dbHelper.blanquearPassword(userId); // Usar el método del DAO
                        usuarios = usuarioDAO.listarTodos(); // Actualiza la lista después de blanquear
                        usuariosAdapter.notifyDataSetChanged(); // Notifica al adaptador para refrescar la lista
                        Toast.makeText(GestionPasswordActivity.this, "Contraseña blanqueada al ID del usuario", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GestionPasswordActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(GestionPasswordActivity.this, "Ingrese un ID de usuario válido", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(GestionPasswordActivity.this, "Ingrese un ID de usuario válido", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
