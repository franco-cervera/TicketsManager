package com.example.ticketsmanager.controller.Admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.dao.MensajeDAO;
import com.example.ticketsmanager.database.DatabaseHelper;
import com.example.ticketsmanager.model.Mensaje;
import com.example.ticketsmanager.controller.Admin.MensajeAdapter;

import java.util.ArrayList;
import java.util.List;

public class GestionMensajesActivity extends AppCompatActivity {
    private MensajeDAO mensajeDAO;
    private RecyclerView recyclerView;
    private MensajeAdapter mensajeAdapter;
    private List<Mensaje> mensajeList;
    private Mensaje mensajeSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_mensajes);

        mensajeDAO = new MensajeDAO(this);
        recyclerView = findViewById(R.id.recyclerViewUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarMensajes();

        Button btnDesblqTec = findViewById(R.id.btnDesblqTec);
        btnDesblqTec.setOnClickListener(v -> marcarComoLeido());


    }


    private void cargarMensajes() {
        mensajeList = mensajeDAO.obtenerMensajes();
        mensajeAdapter = new MensajeAdapter(mensajeList, null);
        recyclerView.setAdapter(mensajeAdapter);
        
        mensajeAdapter.setOnMensajeClickListener(mensaje -> {
            mensajeSeleccionado = mensaje;
            Log.d("Mensaje Seleccionado", "ID: " + mensaje.getIdMensaje() + ", Nombre: " + mensaje.getNombreUsuario());
            Toast.makeText(this, "Técnico: " + mensaje.getNombreUsuario(), Toast.LENGTH_SHORT).show();
        });
    }


    private void marcarComoLeido() {
        if (mensajeSeleccionado != null && mensajeSeleccionado.getEstado().equals("no leído")) {
            mensajeDAO.marcarMensajeComoLeido(mensajeSeleccionado.getIdMensaje());

            cargarMensajes();
            Toast.makeText(this, "El mensaje ha sido marcado como leído.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Por favor, selecciona un mensaje no leído.", Toast.LENGTH_SHORT).show();
        }
    }
}