package com.example.ticketsmanager.model;

public class Usuario {
    protected int id;
    protected String nombreUsuario; // Nuevo campo
    protected String contrasena;
    protected String tipo; // Puede ser "trabajador", "tecnico" o "administrador"

    // Constructor sin ID
    public Usuario(String nombreUsuario, String contrasena, String tipo) {
        this.nombreUsuario = nombreUsuario; // Inicializa el nombre de usuario
        this.contrasena = contrasena;
        this.tipo = tipo;
    }

    // Constructor con ID
    public Usuario(int id, String nombreUsuario, String contrasena, String tipo) {
        this.id = id;
        this.nombreUsuario = nombreUsuario; // Inicializa el nombre de usuario
        this.contrasena = contrasena;
        this.tipo = tipo;
    }

    // Métodos Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario; // Nuevo getter
    }

    public void setNombreUsuario(String nombreUsuario) { // Nuevo setter
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Método para cambiar contraseña
    public void cambiarContrasena(String nuevaContrasena) {
        this.contrasena = nuevaContrasena;
    }
}
