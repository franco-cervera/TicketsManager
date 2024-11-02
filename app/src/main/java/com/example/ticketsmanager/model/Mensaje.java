package com.example.ticketsmanager.model;

public class Mensaje {
    private int idMensaje;
    private int idTecnico;
    private String asunto;
    private String mensaje;
    private String estado;
    private String nombreUsuario; // Asegúrate de que el nombre sea correcto

    public Mensaje(int idMensaje, int idTecnico, String asunto, String mensaje, String estado, String nombreUsuario) {
        this.idMensaje = idMensaje;
        this.idTecnico = idTecnico;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.estado = estado;
        this.nombreUsuario = nombreUsuario; // Esto debe ser correcto
    }


    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public String getAsunto() {
        return asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getEstado() {
        return estado;
    }
}
