package com.example.ticketsmanager.controller.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ticketsmanager.R;

public class AdminDashboardActivity extends AppCompatActivity {

    private CardView cardGestionUsuarios, cardGestionTickets, cardGestionTecnicos, cardGestionPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Inicializar tarjetas
        cardGestionUsuarios = findViewById(R.id.cardGestionUsuarios);
        cardGestionTickets = findViewById(R.id.cardGestionTickets);
        cardGestionTecnicos = findViewById(R.id.cardGestionTecnicos);
        cardGestionPassword = findViewById(R.id.cardGestionPassword);

        // Configurar eventos de clic para cada tarjeta
        cardGestionUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir a la actividad de gestión de usuarios
                Intent intent = new Intent(AdminDashboardActivity.this, GestionUsuariosActivity.class);
                startActivity(intent);
            }
        });

        cardGestionTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir a la actividad de gestión de tickets
                Intent intent = new Intent(AdminDashboardActivity.this, GestionTicketsActivity.class);
                startActivity(intent);
            }
        });

        cardGestionTecnicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir a la actividad de gestión de técnicos
                Intent intent = new Intent(AdminDashboardActivity.this, GestionTecnicosActivity.class);
                startActivity(intent);
            }
        });

        cardGestionPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, GestionPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
