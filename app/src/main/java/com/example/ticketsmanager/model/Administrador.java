package com.example.ticketsmanager.model;

public class Administrador extends Usuario {

    public Administrador(int id, String nombreUsuario, String contrasena, int fallas, int marcas) {
        super(id, nombreUsuario, contrasena, "administrador", fallas, marcas);
    }

}
