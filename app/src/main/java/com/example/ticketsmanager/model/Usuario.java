package com.example.ticketsmanager.model;

public class Usuario {
    protected int id;
    protected String nombreUsuario;
    protected String password;
    protected String tipo;
    protected boolean bloqueado;
    protected int marcas;
    protected int fallas;

    // Constructor sin id ni marcas y fallas (para el registro)
    public Usuario(String nombreUsuario, String password, String tipo) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.tipo = tipo;
        this.bloqueado = false;
        this.marcas = 0;
        this.fallas = 0;
    }

    // Constructor con ID
    public Usuario(int id, String nombreUsuario, String password, String tipo, int marcas, int fallas) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.tipo = tipo;
        this.bloqueado = false;
        this.marcas = marcas;
        this.fallas = fallas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public void cambiarPassword(String nuevaPassword) {
        this.password = nuevaPassword;
    }

    public int getMarcas() {
        return marcas;
    }

    public void setMarcas(int marcas) {
        this.marcas = marcas;
    }

    public int getFallas() {
        return fallas;
    }

    public void setFallas(int fallas) {
        this.fallas = fallas;
    }
}
