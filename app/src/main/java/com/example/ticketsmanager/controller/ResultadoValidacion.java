package com.example.ticketsmanager.controller;

import com.example.ticketsmanager.model.Usuario;

public class ResultadoValidacion {
    public Usuario usuario; // Para almacenar el objeto Usuario
    public String mensaje; // Para almacenar el mensaje de error

    public ResultadoValidacion(Usuario usuario, String mensaje) {
        this.usuario = usuario;
        this.mensaje = mensaje;
    }
}
