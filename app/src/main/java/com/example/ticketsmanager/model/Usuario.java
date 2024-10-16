package com.example.ticketsmanager.model;

public class Usuario {
    protected int id;
    protected String nombreUsuario;
    protected String password;
    protected String tipo;
    protected boolean bloqueado;

    // Constructor sin ID
    public Usuario(String nombreUsuario, String password, String tipo) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.tipo = tipo;
        this.bloqueado = false;
    }

    // Constructor con ID
    public Usuario(int id, String nombreUsuario, String password, String tipo) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.tipo = tipo;
        this.bloqueado = false;
    }

    // Métodos Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) { // Nuevo setter
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() { return password;}

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isBloqueado() { return bloqueado; }

    public void setBloqueado(boolean bloqueado) { this.bloqueado = bloqueado;}


    public void cambiarPassword(String nuevaPassword) {
        this.password = nuevaPassword;
    }
}
