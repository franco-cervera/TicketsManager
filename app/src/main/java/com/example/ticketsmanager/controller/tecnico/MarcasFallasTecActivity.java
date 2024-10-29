package com.example.ticketsmanager.controller.tecnico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.controller.Admin.AdminDashboardActivity;
import com.example.ticketsmanager.controller.Admin.GestionTicketsActivity;
import com.example.ticketsmanager.dao.UsuarioDAO;

public class MarcasFallasTecActivity extends AppCompatActivity {

    private TextView marcatxt2, fallatxt2;
    private UsuarioDAO usuarioDAO;
    private int tecnicoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcas_fallas_tec);

        // Inicializar UsuarioDAO
        usuarioDAO = new UsuarioDAO(this);

        // Obtener el ID del técnico desde el Intent
        tecnicoId = getIntent().getIntExtra("id_tecnico", -1);

        // Enlazar los TextViews del layout
        marcatxt2 = findViewById(R.id.marcatxt2);
        fallatxt2 = findViewById(R.id.fallatxt2);

        // Cargar y mostrar las marcas y fallas del técnico
        cargarDatosTecnico();
    }


    private void cargarDatosTecnico() {
        // Obtener las marcas y fallas del técnico desde la base de datos
        int marcas = usuarioDAO.obtenerMarcasTecnico(tecnicoId);
        int fallas = usuarioDAO.obtenerFallasTecnico(tecnicoId);

        // Actualizar los TextViews con los valores obtenidos
        marcatxt2.setText(String.valueOf(marcas));
        fallatxt2.setText(String.valueOf(fallas));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar los datos cuando la actividad se reanuda
        cargarDatosTecnico();
    }
}
