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

        usuarioDAO = new UsuarioDAO(this);

        tecnicoId = getIntent().getIntExtra("id_tecnico", -1);

        marcatxt2 = findViewById(R.id.marcatxt2);
        fallatxt2 = findViewById(R.id.fallatxt2);

        cargarDatosTecnico();
    }


    private void cargarDatosTecnico() {
        int marcas = usuarioDAO.obtenerMarcasTecnico(tecnicoId);
        int fallas = usuarioDAO.obtenerFallasTecnico(tecnicoId);

        marcatxt2.setText(String.valueOf(marcas));
        fallatxt2.setText(String.valueOf(fallas));
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatosTecnico();
    }
}
