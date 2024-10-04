package com.example.ticketsmanager.dao;

import java.util.List;

public interface DAO <T,K> {
    public abstract void crear (T data);
    public abstract T listar (K id);
    public abstract void actualizar (T data);
    public abstract void eliminar (K id);
    public abstract List<T> listarTodos();




}