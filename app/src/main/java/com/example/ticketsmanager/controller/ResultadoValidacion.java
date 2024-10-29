package com.example.ticketsmanager.controller; // Asegúrate de usar el paquete correcto

import com.example.ticketsmanager.model.Usuario; // Importa la clase Usuario

public class ResultadoValidacion {
    public Usuario usuario; // Para almacenar el objeto Usuario
    public String mensaje; // Para almacenar el mensaje de error

    // Constructor
    public ResultadoValidacion(Usuario usuario, String mensaje) {
        this.usuario = usuario;
        this.mensaje = mensaje;
    }
}
