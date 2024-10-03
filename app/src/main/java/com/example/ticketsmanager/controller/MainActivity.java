package com.example.ticketsmanager.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ticketsmanager.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); // Cambiar a activity_main.xml

        Button btnTrabajador = findViewById(R.id.btnTrabajador);
        Button btnTecnico = findViewById(R.id.btnTecnico);
        Button btnAdministrador = findViewById(R.id.btnAdministrador);

        btnTrabajador.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("userType", "Trabajador");
            startActivity(intent);
        });

        btnTecnico.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("userType", "Tecnico");
            startActivity(intent);
        });

        btnAdministrador.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("userType", "Administrador");
            startActivity(intent);
        });
    }
}
