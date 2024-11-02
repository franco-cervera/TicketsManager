package com.example.ticketsmanager.model;

import java.util.List;

public class Trabajador extends Usuario {

    public Trabajador(int id, String nombreUsuario, String contrasena, int fallas, int marcas) {
        super(id, nombreUsuario, contrasena, "trabajador", fallas, marcas);
    }

}
